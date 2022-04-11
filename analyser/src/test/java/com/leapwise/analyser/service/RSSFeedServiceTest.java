package com.leapwise.analyser.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RSSFeedServiceTest {

    @Test
    public void test1() {
        String title = "French Presidential Election Live Updates: Macron to Face Le Pen in Runoff - The New York Times";
        String result = RSSFeedService.removeSourceFromTitle(title);
        String expected = "French Presidential Election Live Updates: Macron to Face Le Pen in Runoff";
        assert(result.equals(expected));
    }

    @Test
    public void test2() {
        String title = "French Presidential Election Live Updates: Macron to Face Le Pen in Runoff";
        String result = RSSFeedService.removeSourceFromTitle(title);
        String expected = "French Presidential Election Live Updates: Macron to Face Le Pen in Runoff";
        assert(result.equals(expected));
    }
}
