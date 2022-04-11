package com.leapwise.analyser.repository;

import com.leapwise.analyser.model.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Feed Repository
 * @author sbakula
 */
@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

}
