package com.traceprice.takeoffer.controller;

import com.traceprice.takeoffer.dto.Product;
import com.traceprice.takeoffer.service.CrawlerService;
import com.traceprice.takeoffer.service.SearchService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class SearchController {
    @Autowired
    @Qualifier("coupangCrawlerService")
    CrawlerService coupangCrawlerService;

//    @Autowired
//    @Qualifier("gmarketCrawlerService")
//    CrawlerService gmarketCrawlerService;
//
//    @Autowired
//    @Qualifier("aliCrawlerService")
//    CrawlerService aliCrawlerService;
//
//    @Autowired
//    @Qualifier("naverCrawlerService")
//    CrawlerService naverCrawlerService;

    @Autowired
    SearchService searchService;

    @GetMapping("/crawing")
    public CompletableFuture<Void> crawler(@RequestParam String query, @RequestParam List<String> ban , Model model) throws IOException, InterruptedException {
        return coupangCrawlerService.getSearchResults();
    }
    @GetMapping("/appleing")
    public CompletableFuture<Void> crawler(@RequestParam String query, Model model) throws IOException, InterruptedException {
        return coupangCrawlerService.getAppleResults();
    }

    @GetMapping("/super")
    public CompletableFuture<Void> mainCrawler() throws IOException, InterruptedException {
        Long start = System.currentTimeMillis();
        CompletableFuture<Void> a = coupangCrawlerService.getSearchResults();
        Long aT = System.currentTimeMillis();
        CompletableFuture<Void> b = coupangCrawlerService.categoryResults();
        Long bT = System.currentTimeMillis();
        CompletableFuture<Void> c = coupangCrawlerService.getAppleResults();
        Long end = System.currentTimeMillis();
        System.err.println((aT-start)/60000.0);
        System.err.println((bT-aT)/60000.0);
        System.err.println((end-bT)/60000.0);
        System.err.println((end-start)/60000.0);
        return CompletableFuture.allOf(a, b, c);
    }

    @GetMapping("/searching")
    public Page<Product> search(@RequestParam String query, Model model,
                                @RequestParam(required = false, defaultValue = "0") String options,
                                @PageableDefault(size = 36, page = 0) Pageable pageable){
//        Page<Product> coupangProducts = searchService.Search(query, options, pageable);
//        System.out.println(coupangProducts.getTotalPages());
        model.addAttribute("search", query);
//        model.addAttribute("coupangProducts", coupangProducts);
        System.err.println("dd " +query);
        return searchService.search(query, options, pageable);
    }
}
