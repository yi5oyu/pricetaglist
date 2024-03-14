package com.traceprice.takeoffer.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CoupangCrawlerService implements CrawlerService {
    @Override
    public List<List<String>> getSearchResults(String q) throws IOException {
        List<List<String>> results = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<String> price = new ArrayList<>();
        Random random = new Random();
        int randomTimeout = random.nextInt(5000 + 1) + 3000;
        String url = "https://www.coupang.com/np/search?component=&q=" + q;
        Document d = Jsoup.connect(url)
                .timeout(randomTimeout)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                .get();
        Elements e = d.select(".name");
        e.forEach(element -> name.add(element.text()));
        e = d.select(".price-value");
        e.forEach(element -> price.add(element.text()));
        results.add(name);
        results.add(price);
        return results;
    }
}
