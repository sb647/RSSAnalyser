package com.leapwise.analyser.model.dto;

import com.leapwise.analyser.model.Feed;
import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FeedDTO {
    private String title;
    private String link;

    public static FeedDTO fromEntity(Feed feed) {
        FeedDTO feedDTO = new FeedDTO();
        feedDTO.setTitle(feed.getTitle());
        feedDTO.setLink(feed.getLink());
        return feedDTO;
    }

    public static List<FeedDTO> fromEntityList(Collection<Feed> feedList){
        return feedList.stream().map(FeedDTO::fromEntity).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "[ " +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ']';
    }


}
