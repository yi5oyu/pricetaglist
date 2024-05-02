package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.dto.Product;

import java.io.IOException;
import java.util.List;

public interface CrawlerService {
    void getSearchResults(String query, List<String> ban) throws IOException, InterruptedException;
}
