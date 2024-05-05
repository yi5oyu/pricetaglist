package com.traceprice.takeoffer.controller;

import com.traceprice.takeoffer.dto.Product;
import com.traceprice.takeoffer.service.CrawlerService;
import com.traceprice.takeoffer.service.SearchService;
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

    @Autowired
    SearchService searchService;

    @GetMapping("/crawing")
    public String crawler(@RequestParam String query, @RequestParam List<String> ban , Model model) throws IOException, InterruptedException {
        System.out.println(query + " + " + ban.get(0));
        coupangCrawlerService.getSearchResults(query, ban);

//        List<Product> gmarketContents = gmarketCrawlerService.getSearchResults(query);
//        List<Product> aliContents = aliCrawlerService.getSearchResults(query);

//        model.addAttribute("coupangContents", coupangProducts);
//        model.addAttribute("gmarketContents", gmarketContents);
//        model.addAttribute("aliContents", aliContents);

        return "crawlerdisplay";
//        crawler or /crawler
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model,
                        @RequestParam(required = false, defaultValue = "0") String options){
        List<Product> coupangProducts = searchService.Search(query, options);
        System.out.println(coupangProducts.size());
        model.addAttribute("search", query);
        model.addAttribute("coupangProducts", coupangProducts);
        return "searchPage";
    }
}
