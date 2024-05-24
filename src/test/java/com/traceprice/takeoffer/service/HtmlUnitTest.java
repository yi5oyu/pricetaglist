package com.traceprice.takeoffer.service;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HtmlUnitTest {

    private WebClient webClient;

    @BeforeEach
    public void init() {
        webClient = new WebClient();
        webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3");
        webClient.addRequestHeader("Accept-Language", "ko,ja;q=0.9,en;q=0.8,en-US;q=0.7");
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.waitForBackgroundJavaScript(5000);

    }

    @AfterEach
    public void close() {
        webClient.close();
    }

    @Test
    public void pageTitleTest() throws Exception {
        // WebClient를 사용하여 테스트할 웹 페이지로 이동
//        String url = "https://www.coupang.com/np/search?rocketAll=true&listSize=72&component=&q=노트북&page=3";
//        HtmlPage page = webClient.getPage(url);
//        page.executeJavaScript("window.scrollTo(0, document.body.scrollHeight)");
//
//        DomNodeList<DomNode> productElements = page.querySelectorAll(".search-product");
        int a = 0;
//        for (DomNode productEl : productElements) {
//            // 이미지 URL 추출
//            a++;
//            DomNode imageElement = productEl.querySelector(".search-product-wrap-img");
//            String img = imageElement != null ? imageElement.getAttributes().getNamedItem("src").getNodeValue() : "";
//            if (img.isEmpty() && imageElement != null) {
//                img = imageElement.getAttributes().getNamedItem("data-src").getNodeValue();
//            }
//
//            // 상품명 추출
//            DomNode nameElement = productEl.querySelector(".name");
//            String pname = nameElement != null ? nameElement.getTextContent().trim() : "";
//
//            // 가격 추출
//            DomNode priceElement = productEl.querySelector(".price-value");
//            String price = priceElement != null ? priceElement.getTextContent().trim() : "";
//
//            // 상품 페이지 URL 추출
//            String pid = "https://www.coupang.com";
//            DomNode linkElement = productEl.querySelector(".search-product-link");
//            if (linkElement != null) {
//                pid += linkElement.getAttributes().getNamedItem("href").getNodeValue();
//            }
//
//            System.out.println("Image: " + img);
////            System.out.println("Name: " + pname);
////            System.out.println("Price: " + price);
////            System.out.println("Product URL: " + pid);
////            System.out.println("======================================");
//        }
        System.out.println("fff: "+ a);
    }

    }
