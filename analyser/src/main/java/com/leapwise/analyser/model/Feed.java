package com.leapwise.analyser.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Feed Entity Class
 * @author sbakula
 */
@Data
@NoArgsConstructor
@Entity
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;

    @Column(length = 1000)
    private String link;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "feed_id", referencedColumnName = "id")
    private List<FeedTopic> feedTopicList = new ArrayList<>();

}
