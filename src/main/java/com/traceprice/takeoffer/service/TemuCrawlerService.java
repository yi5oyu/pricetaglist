package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.dto.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TemuCrawlerService implements CrawlerService{
    //동적
    @Override
    public List<Product> getSearchResults(String q) throws IOException {
        List<Product> results = new ArrayList<>();
        Random random = new Random();
        int randomTimeout = random.nextInt(5000 + 1) + 3000;
        String url = "https://www.temu.com/kr?search_result.html%3Fsearch_key=" + q;
        Document d = Jsoup.connect(url)
                .timeout(randomTimeout)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                .get();
        System.out.println(d.toString());
        Elements e = d.select("._2XmIMTf3");
//        e.forEach(element -> results.add(element.text()));
        return results;
    }
}
