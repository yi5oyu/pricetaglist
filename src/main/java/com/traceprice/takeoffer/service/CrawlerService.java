package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.dto.Product;
import com.traceprice.takeoffer.entity.ProductInfoByDate;
import org.springframework.data.jpa.repository.Query;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public interface CrawlerService {
    void getSearchResults(String query, List<String> ban) throws IOException, InterruptedException;

    void getAppleResults(String query) throws IOException, InterruptedException;
}
