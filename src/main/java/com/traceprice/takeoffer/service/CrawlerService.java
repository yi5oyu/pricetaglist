package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.dto.Product;

import java.io.IOException;
import java.util.List;

public interface CrawlerService {
    List<Product> getSearchResults(String query) throws IOException, InterruptedException;
}
