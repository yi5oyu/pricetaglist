package com.traceprice.takeoffer.controller;

import com.traceprice.takeoffer.service.CrawlerService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    @Qualifier("coupangCrawlerService")
    CrawlerService coupangCrawlerService;

    @Autowired
    @Qualifier("gmarketCrawlerService")
    CrawlerService gmarketCrawlerService;

    @Autowired
    @Qualifier("aliCrawlerService")
    CrawlerService aliCrawlerService;

    @Autowired
    @Qualifier("naverCrawlerService")
    CrawlerService naverCrawlerService;

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) throws IOException {
        List<List<String>> results = coupangCrawlerService.getSearchResults(query);
        model.addAttribute("results", results);
//          System.out.println("go"+ query+"fff: "+ url);
        return "layout/main";
    }
}
