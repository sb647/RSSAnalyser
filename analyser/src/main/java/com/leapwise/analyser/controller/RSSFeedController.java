package com.leapwise.analyser.controller;

import com.leapwise.analyser.exception.URLParserException;
import com.leapwise.analyser.model.dto.HotTopic;
import com.leapwise.analyser.service.HotTopicService;
import com.leapwise.analyser.service.RSSFeedService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RSS Feeds Analyser Controller
 * @author sbakula
 */

@RestController
@AllArgsConstructor
public class RSSFeedController {

    private final RSSFeedService feedService;
    private final HotTopicService hotTopicService;

    /**
     * Takes at least two RSS Feeds URLs and parses their content.
     * Stores feeds and extracted topics from feed title.
     * @param URLs List of RSS Feeds URLs
     * @return unique hot topic analysis identifier, used to fetch analyzed data
     */
    @PostMapping("/analyse/new")
    public ResponseEntity<String> analyseFeeds(@RequestBody String... URLs) {
        if(URLs.length < 2) {
            return ResponseEntity.badRequest().body("At least two valid URLs should be provided.");
        }
        String response;
        try{
            response = feedService.processRSSFeeds(URLs);
        } catch(URLParserException ex) {
            return ResponseEntity.internalServerError().body("An error occurred while parsing data.");
        }
        return ResponseEntity.ok(response);
    }


    /**
     * Searches for the most common topics in datastore
     * and fetches feeds related to these topics.
     * @param id unique hot topic analysis identifier
     * @return list of hot topics and related feeds for each topic
     */
    @GetMapping("/frequency/{id}")
    public List<HotTopic> fetchHotTopics(@PathVariable String id) {
        return hotTopicService.fetchHotTopics(id);
    }
}
