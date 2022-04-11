package com.leapwise.analyser.repository;

import com.leapwise.analyser.model.Feed;
import com.leapwise.analyser.model.FeedTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Feed Topic Repository
 * @author sbakula
 */
@Repository
public interface FeedTopicRepository extends JpaRepository<FeedTopic, Long> {


    /**
     * Fetches topics related to a given analysis identifier and
     * sorts them by number of occurrences
     * @param id analysis identifier
     * @return list of topic names, sorted by number of occurrences
     * in descending order
     */
    @Query(
            value = " SELECT topic.topic "+
                    " FROM FeedTopic as topic WHERE topic.analysisID = ?1 " +
                    " GROUP BY topic.topic "+
                    " ORDER BY COUNT(*) DESC"
    )
    Collection<String> findHotTopics(String id);

    /**
     * Fetches topics with requested topic name and analysis
     * identifier and returns stored feeds
     * @param topic topic name
     * @param id analysis identifier
     * @return list of feeds
     */
    @Query(
            value = " SELECT topic.feed " +
                    " FROM FeedTopic as topic " +
                    " WHERE topic.analysisID = ?2 AND topic.topic = ?1"
    )
    Collection<Feed> findRelatedFeeds(String topic, String id);

}
