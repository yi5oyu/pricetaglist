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
public class GmarketCrawlerService implements CrawlerService{
    @Override
    public List<Product> getSearchResults(String q) throws IOException {
        List<Product> results = new ArrayList<>();
//        Random random = new Random();
//        int randomTimeout = random.nextInt(5000 + 1) + 3000;
        String url = "https://browse.gmarket.co.kr/search?keyword=" + q;
        Document d = Jsoup.connect(url)
//                .timeout(randomTimeout)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0")
                .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
//                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                .get();

        Elements productElements = d.select(".box__item-container");
        for (Element productEl : productElements) {
            String img = "//gdimg.gmarket.co.kr/";
            img += productEl.select(".link__item").attr("data-montelena-goodscode");
            img += "/still/280";
            String pname = productEl.select(".text__item").text(); // 상품 이름
            String price = productEl.select("strong.text__value").text(); // 가격
            String pid = productEl.select(".link__item").attr("href");
            Product product = Product.builder()
                    .img(img)
                    .pName(pname)
                    .price(price)
                    .pID(pid)
                    .build();

            results.add(product);
        }

        return results;
    }
}
