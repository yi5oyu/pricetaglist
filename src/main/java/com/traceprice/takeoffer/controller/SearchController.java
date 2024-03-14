package com.traceprice.takeoffer.controller;

import com.traceprice.takeoffer.dto.Product;
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
        System.out.println(query);
        List<Product> coupangContents = coupangCrawlerService.getSearchResults(query);
        List<Product> gmarketContents = gmarketCrawlerService.getSearchResults(query);
//        List<Product> aliContents = aliCrawlerService.getSearchResults(query);

        model.addAttribute("coupangContents", coupangContents);
        model.addAttribute("gmarketContents", gmarketContents);
//        model.addAttribute("aliContents", aliContents);

        model.addAttribute("search", query);
        return "home";
    }
}
