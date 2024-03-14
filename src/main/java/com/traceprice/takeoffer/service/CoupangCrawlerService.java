package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.dto.Product;
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
    public List<Product> getSearchResults(String q) throws IOException {
        List<Product> results = new ArrayList<>();
        Random random = new Random();
        int randomTimeout = random.nextInt(5000 + 1) + 3000;
        String url = "https://www.coupang.com/np/search?component=&q=" + q;
        Document d = Jsoup.connect(url)
                .timeout(randomTimeout)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                .get();

        Elements productElements = d.select(".search-product-link"); // 상품 리스트를 선택하는 적절한 선택자 사용
        for (Element productEl : productElements) {
            String img = productEl.select(".search-product-wrap-img").attr("src"); // 이미지 URL
            System.out.println(img);
            String pname = productEl.select(".name").text(); // 상품 이름
            String price = productEl.select(".price-value").text(); // 가격

            Product product = Product.builder()
                    .img(img)
                    .pName(pname)
                    .price(price)
                    .build();

            results.add(product);
        }


        return results;
    }
}
