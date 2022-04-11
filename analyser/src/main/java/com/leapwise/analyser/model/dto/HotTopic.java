package com.leapwise.analyser.model.dto;


import com.leapwise.analyser.model.Feed;
import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * Represents an object that contains the name of the
 * hot topic and list of feeds related to that topic
 *
 * @author sbakula
 */
@Data
public class HotTopic {
    private String topic;
    private List<FeedDTO> relatedFeedList;

    public HotTopic(String topic, Collection<Feed> relatedFeeds) {
        this.topic = topic;
        this.relatedFeedList = FeedDTO.fromEntityList(relatedFeeds);
    }
}
