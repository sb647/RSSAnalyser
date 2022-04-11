package com.leapwise.analyser.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Feed Topic Entity Class
 * @author sbakula
 */
@Data
@NoArgsConstructor
@Entity
public class FeedTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String analysisID;
    private String topic;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Feed feed;
}
