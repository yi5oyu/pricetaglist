package com.traceprice.takeoffer.service;

import java.io.IOException;
import java.util.List;

public interface CrawlerService {
    List<List<String>> getSearchResults(String query) throws IOException;
}
