package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.dto.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AliCrawlerService implements CrawlerService {
    @Override
    public void getSearchResults(String q) throws IOException {
        List<Product> results = new ArrayList<>();
        Random random = new Random();
        int randomTimeout = random.nextInt(5000 + 1) + 3000;
        String url = "https://ko.aliexpress.com/w/wholesale-" + q + ".html";
        Document d = Jsoup.connect(url)
                .timeout(randomTimeout)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                .get();

        Elements productElements = d.select(".card--out-wrapper");
        for (Element productEl : productElements) {
            String img = productEl.select("div[class^=images--hover--]").attr("src");
            System.out.println(img);
            String pname = productEl.select("h3[class^=multi--titleText--]").text(); // 상품 이름
            Elements priceSpans = productEl.select("div[class^=multi--price-sale--] span");
            StringBuilder priceBuilder = new StringBuilder();
            for (Element span : priceSpans) {
                priceBuilder.append(span.text());
            }
            String price = priceBuilder.toString();
            String pid = productEl.select(".search-card-item").attr("href");
            System.out.println(pid);
//            Product product = Product.builder()
//                    .img(img)
//                    .pName(pname)
//                    .price(price)
//                    .pID(pid)
//                    .build();

//            results.add(product);
        }
//        return results;
    }
}
