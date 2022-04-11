package com.leapwise.analyser.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Topic Extractor Service
 * @author sbakula
 */
@Service
public class TopicExtractorService {

    private List<String> stopwords;

    public TopicExtractorService(@Value("${stopwords-path}") String path) {
        try {
            stopwords = Files.readAllLines(Paths.get(path));
        } catch (IOException ex) {
            stopwords = new ArrayList<>();
        }
    }

    /**
     * Removes stopwords from given string and returns a list whose
     * elements are words from given string
     * @param string string to be tokenized
     * @return list of words
     */
    public Set<String> extractWords(String string) {
        string = string.toLowerCase();
        string = string.replaceAll("[^\sa-z0-9]", "");
        Set<String> words = new HashSet<>(Arrays.asList(string.split("\\s+")));
        words.removeAll(stopwords);
        return words;
    }
}
