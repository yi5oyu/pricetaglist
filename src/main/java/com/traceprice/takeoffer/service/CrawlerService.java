package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.dto.Product;
import com.traceprice.takeoffer.entity.ProductInfoByDate;
import org.springframework.data.jpa.repository.Query;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CrawlerService {
    CompletableFuture<Void> getSearchResults(String query, List<String> ban) throws IOException, InterruptedException;

    CompletableFuture<Void> getAppleResults(String query) throws IOException, InterruptedException;
}
