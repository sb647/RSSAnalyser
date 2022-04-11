package com.leapwise.analyser.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class TopicExtractorServiceTest {

    private final TopicExtractorService topicExtractorService;

    @Autowired
    public TopicExtractorServiceTest(TopicExtractorService topicExtractorService) {
        this.topicExtractorService = topicExtractorService;
    }

    @Test
    public void test1() {
        String title = "French Presidential Election Live Updates: Macron to Face Le Pen in Runoff - The New York Times";
        Set<String> result = topicExtractorService.extractWords(title);
        List<String> expected = Arrays.asList("french", "presidential", "election", "live", "updates", "macron", "face", "le", "pen", "runoff");
        assert (result.containsAll(expected));
    }

    @Test
    public void test2() {
        String title = "Murder charges to be dropped for Texas woman arrested over abortion - The Washington Post";
        Set<String> result = topicExtractorService.extractWords(title);
        List<String> stopwords = Arrays.asList("to", "be", "for", "over");
        stopwords.forEach(stopword -> {
            assert (!result.contains(stopword));
        });

    }


}
