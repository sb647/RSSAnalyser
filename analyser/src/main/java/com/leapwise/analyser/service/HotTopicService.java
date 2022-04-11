package com.leapwise.analyser.service;

import com.leapwise.analyser.model.Feed;
import com.leapwise.analyser.model.dto.HotTopic;
import com.leapwise.analyser.repository.FeedTopicRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hot Topic Service
 * @author sbakula
 */
@Service
public class HotTopicService {

    private final FeedTopicRepository feedTopicRepository;

    private final Integer size;

    public HotTopicService(@Value("${size}") Integer size, FeedTopicRepository feedTopicRepository) {
        this.size = size;
        this.feedTopicRepository = feedTopicRepository;
    }

    /**
     * Finds the most popular topics for requested analysis and
     * fetches related feeds
     * @param id unique analysis identifier
     * @return list of hot topics and related feeds
     */
    public List<HotTopic> fetchHotTopics(String id) {
        Collection<String> hotTopics = feedTopicRepository.findHotTopics(id).stream().limit(size).collect(Collectors.toList());

        return hotTopics.stream().map(topic -> {
            Collection<Feed> feedList = feedTopicRepository.findRelatedFeeds(topic, id);
            return new HotTopic(topic, feedList);
        }).collect(Collectors.toList());
    }
}
