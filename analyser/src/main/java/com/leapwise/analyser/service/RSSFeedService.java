package com.leapwise.analyser.service;

import com.leapwise.analyser.exception.URLParserException;
import com.leapwise.analyser.model.Feed;
import com.leapwise.analyser.model.FeedTopic;
import com.leapwise.analyser.repository.FeedRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * RSS Feeds Service
 * @author sbakula
 */
@Service
@AllArgsConstructor
public class RSSFeedService {

    private final TopicExtractorService topicExtractor;
    private final FeedRepository feedRepository;

    private static final String SOURCE_SEPARATOR = "-";

    /**
     * Creates and returns a unique analysis identifier.
     * Invokes method that process and stores data from given URLs
     * @param URLs RSS Feeds URLs
     * @return unique hot topic analysis identifier
     */
    public String processRSSFeeds(String[] URLs) {
        UUID uuid = UUID.randomUUID();
        Arrays.stream(URLs).forEach(URL -> {
            try {
                extractFeeds(URL, uuid);
            } catch (ParserConfigurationException | IOException | SAXException e) {
                e.printStackTrace();
                throw new URLParserException("An error occurred while processing URL.");
            }
        });
        return uuid.toString();
    }

    /**
     * Parses feeds from given URL, extracts topics from
     * title for each feed and stores extracted data.
     * @param URL RSS Feed URL
     * @param uuid unique analysis identifier, attribute to be stored with topic data
     * @throws ParserConfigurationException when a configuration error occurs in DocumentBuilder
     * @throws IOException when a problem occurs while fetching URL
     * @throws SAXException when a problem occurs while parsing URL content
     */
    public void extractFeeds(String URL, UUID uuid) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(URL).openStream());
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("item");
        IntStream.range(0, nodeList.getLength()).forEach(i -> {
            Element item = (Element) nodeList.item(i);
            String title = item.getElementsByTagName("title").item(0).getTextContent();
            title = removeSourceFromTitle(title);
            String link = item.getElementsByTagName("link").item(0).getTextContent();
            Set<String> topics = topicExtractor.extractWords(title);
            List<FeedTopic> topicList = topics.stream().map(topic -> {
                FeedTopic feedTopic = new FeedTopic();
                feedTopic.setTopic(topic);
                feedTopic.setAnalysisID(uuid.toString());
                return feedTopic;
            }).collect(Collectors.toList());

            Feed feed = new Feed();
            feed.setLink(link);
            feed.setTitle(title);
            feed.setFeedTopicList(topicList);
            this.feedRepository.save(feed);
        });
    }

    /**
     * Removes source/publisher name from title
     * @param title Feed title
     */
    public static String removeSourceFromTitle(String title) {
        if (title.contains(SOURCE_SEPARATOR)) return title.substring(0, title.lastIndexOf(SOURCE_SEPARATOR)).trim();
        return title;
    }
}
