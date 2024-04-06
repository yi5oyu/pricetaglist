package com.traceprice.takeoffer.service;

import com.traceprice.takeoffer.dto.Product;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SeleniumTest {

    private static WebDriver webDriver;

    @BeforeAll
    static void setupClass(){
        WebDriverManager.edgedriver().setup();


//        ChromeOptions options = new ChromeOptions();
        EdgeOptions options = new EdgeOptions();
//        options.addArguments("--headless");
//        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//        options.addArguments("--Accept-Language=ko,ja;q=0.9,en;q=0.8,en-US;q=0.7");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36 Edg/122.0.0.0");
        options.addArguments("--lang=ko_KR");
//        options.addArguments("--disable-infobars"); // 정보 바 비활성화
        options.addArguments("--incognito"); // 시크릿 모드 활성화
//        options.addArguments("--disable-default-apps"); // 기본 앱 비활성화
//        options.addArguments("--disable-popup-blocking"); // 팝업 차단 비활성화

        webDriver = new EdgeDriver(options);
    }

    @AfterAll
    static void closeClass(){
        if(webDriver != null)
            webDriver.quit();
    }

    @Test
//    @Disabled("학인")
    public void coupangCrawlingTest() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        for(int i = 10 ; i<15 ;i++){
            int count = 0;
            Random r = new Random();
            int randomTimeout = r.nextInt(5) + 3;
            webDriver.get("https://www.coupang.com/np/search?rocketAll=true&listSize=72&component=&q=노트북&page="+ i);
            TimeUnit.SECONDS.sleep(randomTimeout);
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".search-product")));

            List<WebElement> productElements = webDriver.findElements(By.cssSelector(".search-product"));
            for (WebElement productEl : productElements) {
                count++;
                String img = productEl.findElement(By.cssSelector(".search-product-wrap-img")).getAttribute("src");
                if (img.isEmpty()) {
                    img = productEl.findElement(By.cssSelector(".search-product-wrap-img")).getAttribute("data-src");
                }
                String pname = productEl.findElement(By.cssSelector(".name")).getText();
                WebElement priceElement = productEl.findElement(By.cssSelector(".price-value"));
                String price = priceElement.getText();
                String pid = productEl.getAttribute("href");
                if(!pname.equals("") || pname.contains("파우치")){
//                    Product p = Product.builder().pName(pname).price(price).img(img).pID(pid).build();
    //                assertNull(p,p.toString());
//                    System.out.println("page "+i+ "= "+p.toString());
                }
            }
            System.out.println("count ="+count);
            List<WebElement> btn = webDriver.findElements(By.cssSelector(".btn-next"));
            if(btn.size() > 0){
                Boolean b = btn.get(0).getAttribute("class").contains("disabled");
                System.out.println("i :"+i +" b : "+b);
                if(b)
                    break;
            }
        }
    }

    @Test
    public void aliCrawlingTest() {
//        webDriver.get("https://www.coupang.com/np/search?rocketAll=true&listSize=72&component=&q=노트북&page=);

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
    }

}
