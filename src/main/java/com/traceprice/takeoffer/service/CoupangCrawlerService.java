package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.dto.Product;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class CoupangCrawlerService implements CrawlerService {
    @Override
    public List<Product> getSearchResults(String q) throws IOException {
        List<Product> results = new ArrayList<>();
//        Random random = new Random();
//        int randomTimeout = random.nextInt(5000 + 1) + 3000;
//        String url = "https://www.coupang.com/np/search?component=&q=" + q;
//        Document d = Jsoup.connect(url)
////                .timeout(randomTimeout)
//                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
//                .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
//                .get();
//
//        Elements productElements = d.select(".search-product-link"); // 상품 리스트를 선택하는 적절한 선택자 사용
//        for (Element productEl : productElements) {
//            // 이미지 URL 확인, 'data-src' 속성 또는 'src' 속성 확인
//            String img = productEl.select(".search-product-wrap-img").attr("src");
//            if (img.isEmpty()) { // 'data-src'가 비어있는 경우 'src'를 대신 사용
//                img = productEl.select(".search-product-wrap-img").attr("data-src");
//            }
//            if (img.contains("blank1x1.gif")) { // 플레이스홀더 이미지 필터링
//                continue; // 플레이스홀더 이미지인 경우, 리스트에 추가하지 않고 다음 상품으로 넘어감
//            }
//
//            String pname = productEl.select(".name").text(); // 상품 이름
//            String price = productEl.select(".price-value").text(); // 가격
//            String pid = "https://www.coupang.com/";
//            pid += productEl.select(".search-product-link").attr("href");
//
//            Product product = Product.builder()
//                    .img(img)
//                    .pName(pname)
//                    .price(price)
//                    .pID(pid)
//                    .build();
//
//            results.add(product);
//        }

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36");

        WebDriver webDriver = new ChromeDriver(options);

        try {
            webDriver.get("https://www.coupang.com/np/search?component=&q=" + q);

            List<WebElement> productElements = webDriver.findElements(By.cssSelector(".search-product-link"));
            for (WebElement productEl : productElements) {
                String img = productEl.findElement(By.cssSelector(".search-product-wrap-img")).getAttribute("src");
                if (img.isEmpty()) {
                    img = productEl.findElement(By.cssSelector(".search-product-wrap-img")).getAttribute("data-src");
                }
                String pname = productEl.findElement(By.cssSelector(".name")).getText();
                System.out.println(pname);
                String price = productEl.findElement(By.cssSelector(".price-value")).getText();
                String pid = productEl.getAttribute("href");

                Product product = new Product(img,pname,price,pid);

                results.add(product);
            }
        } finally {
            if (webDriver != null) {
                webDriver.quit();
            }
        }



        return results;
    }
}
