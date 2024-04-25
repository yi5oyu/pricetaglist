package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.dto.Product;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CoupangCrawlerService implements CrawlerService {
    @Override
    public List<Product> getSearchResults(String q) throws IOException, InterruptedException {
        List<Product> results = new ArrayList<>();
        int count = 13;
        int num = 0;
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
    while (true){
        Random random = new Random();
//        int randomTimeout = random.nextInt(10000 + 1) + 30000;
        int randomTimeout = random.nextInt(1) + 1;
        TimeUnit.SECONDS.sleep(randomTimeout);
        System.out.println(count);
        String url = "https://www.coupang.com/np/search?rocketAll=true&listSize=72&component=&q=" + q + "&page=" + count;
        Document d = Jsoup.connect(url)
//                .timeout(randomTimeout)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                .get();
        count++;
        Elements productElements = d.select(".search-product");
        for (Element productEl : productElements) {
            num++;
//            String img = productEl.select(".search-product-wrap-img").attr("src");
//            if (img.isEmpty()) {
//                img = productEl.select(".search-product-wrap-img").attr("data-src");
//            }
//            if (img.contains("blank1x1.gif")) {
//                continue;
//            }

            String name = productEl.select(".name").text();
            String price = productEl.select(".price-value").text();

            if(!name.contains("파우치") && !name.contains("쿨러") && !name.contains("가방")){

                String address = "https://www.coupang.com";
                address += productEl.select(".search-product-link").attr("href");
                Pattern pattern = Pattern.compile("products/(\\d+)\\?itemId=(\\d+)");
                Matcher matcher = pattern.matcher(address);
                Long product_number = 0L;
                Long item_number = 0L;
                if(matcher.find()){
                    product_number = Long.parseLong(matcher.group(1));
                    item_number = Long.parseLong(matcher.group(2));
                }
                randomTimeout = random.nextInt(3) + 2;
                TimeUnit.SECONDS.sleep(randomTimeout);
                Document p = Jsoup.connect(address)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                        .header("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7")
                        .get();
                String img = p.select(".prod-image__detail").attr("src");
                System.out.println(randomTimeout +" : "+ img);
                String full_name = p.select(".prod-buy-header__title").text();
                String detail_info = p.select(".prod-buy-header__attribute-title").text();
                String pname = "";
                if(!detail_info.isEmpty()){
                    pname = full_name.replace(detail_info, "").trim();
                    detail_info = detail_info.replaceAll(" · " , ",");
                    System.out.println(detail_info);
                } else
                    pname = full_name;
                System.out.println(pname + "-- "+ detail_info);
                Long fixed_price = 0L;
                String orign_price = p.select(".origin-price").text();
                if(!orign_price.isEmpty())
                    fixed_price = Long.parseLong(orign_price.replaceAll("[^0-9]", ""));
                System.out.println("fixed : " + fixed_price);
                int discount_rate = 0;
                String discount = p.select(".discount-rate").text();
                if(!discount.isEmpty())
                    discount_rate = Integer.parseInt(discount.replaceAll("[^0-9]", ""));
                System.out.println("dis : "+discount_rate);
                Long daily_price = 0L;
                String total_price = p.select(".total-price > strong").last().text();
                if(!total_price.isEmpty()){
                    daily_price = Long.parseLong(total_price.replaceAll("[^0-9]", ""));
                }
                System.out.println(daily_price);
                int item_quantity = 101;
                String quantity = p.select(".aos-label").text();
                String out_of_stock = p.select(".oos-label").text();
                if(!quantity.isEmpty()){
                    item_quantity = Integer.parseInt(quantity);
                }
                if(!out_of_stock.isEmpty()){
                    item_quantity = 0;
                }

                // 고정가 .origin-price empty 와 Long 타입과 할인률
                if (!pname.equals("")) {
                    Product product = Product.builder()
                            .market_name("쿠팡")
                            .product_type("노트북")
                            .product_number(product_number)
                            .pname(pname)
                            .fixed_price(fixed_price)
                            .item_img(img)
                            .item_number(item_number)
                            .price_date(currentDate)
                            .daily_price(daily_price)
                            .delivery_type("로켓배송")
                            .discount_rate(discount_rate)
                            .detail_info(detail_info)
                            .address(address)
                            .item_quantity(item_quantity)
                            .build();
                    results.add(product);
                }
            }
        }
        System.out.println("개수 : "+ num);
        num = 0;
        Elements btn = d.select(".btn-next");
        if (btn.size() > 0) {
            Boolean b = btn.get(0).attr("class").contains("disabled");
            System.out.println("b:" + b);
            if (b)
                break;
        }
    }
//        WebDriverManager.edgedriver().setup();
////        ChromeOptions options = new ChromeOptions();
//        EdgeOptions options = new EdgeOptions();
////        options.addArguments("--headless");
//        options.addArguments("--disable-gpu");
//        options.addArguments("--window-size=1920,1080");
//        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0");
//        options.addArguments("--lang=ko_KR");

//
//        WebDriver webDriver = new EdgeDriver(options);
//        try {
//            for(int i = 10 ; i<15 ;i++){
//                int count = 0;
//                Random r = new Random();
//                int randomTimeout = r.nextInt(5) + 3;
//                webDriver.get("https://www.coupang.com/np/search?rocketAll=true&listSize=72&component=&q="+q+"&page="+ i);
//                TimeUnit.SECONDS.sleep(randomTimeout);
//                List<WebElement> productElements = webDriver.findElements(By.cssSelector(".search-product"));
//                for (WebElement productEl : productElements) {
//                    count++;
//                    String img = productEl.findElement(By.cssSelector(".search-product-wrap-img")).getAttribute("src");
//                    if (img.isEmpty()) {
//                        img = productEl.findElement(By.cssSelector(".search-product-wrap-img")).getAttribute("data-src");
//                    }
//                    String pname = productEl.findElement(By.cssSelector(".name")).getText();
//                    WebElement priceElement = productEl.findElement(By.cssSelector(".price-value"));
//                    String price = priceElement.getText();
//                    String pid = productEl.getAttribute("href");
//                    if(!pname.equals("") || pname.contains("파우치")){
//                        Product p = Product.builder().pName(pname).price(price).img(img).pID(pid).build();
//                        //                assertNull(p,p.toString());
//                        System.out.println("page "+i+ "= "+p.toString());
//                    }
//                }
//                System.out.println("count ="+count);
//                List<WebElement> btn = webDriver.findElements(By.cssSelector(".btn-next"));
//                if(btn.size() > 0){
//                    Boolean b = btn.get(0).getAttribute("class").contains("disabled");
//                    System.out.println("i :"+i +" b : "+b);
//                    if(b)
//                        break;
//                }
//            }
//
//        } finally {
//            if (webDriver != null) {
//                webDriver.quit();
//            }
//        }

        return results;
    }
}
