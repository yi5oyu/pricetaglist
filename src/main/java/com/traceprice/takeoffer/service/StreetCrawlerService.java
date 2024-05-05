package com.traceprice.takeoffer.service;


import com.traceprice.takeoffer.dto.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
public class StreetCrawlerService  { // implements CrawlerService
    //동적
//    @Override
    public void getSearchResults(String q, List<String> ban) {
        List<Product> results = new ArrayList<>();

        // HtmlUnit WebClient를 사용
//        try (final WebClient webClient = new WebClient()) {
//            webClient.getOptions().setCssEnabled(false);
//            webClient.getOptions().setJavaScriptEnabled(true); // JavaScript를 활성화
//            webClient.getOptions().setThrowExceptionOnScriptError(false); // 스크립트 오류 발생 시 예외를 던지지 않음
//
//            String url = "https://search.11st.co.kr/pc/total-search?kwd=" + q;
//            HtmlPage page = webClient.getPage(url);
//
//            // 페이지가 완전히 로드되고 JavaScript가 실행될 시간을 기다립니다.
//            webClient.waitForBackgroundJavaScriptStartingBefore(10000); // 최대 10초 동안 대기
//
//            // 페이지 내에서 원하는 요소(예: 제품 이름)를 선택합니다. 적절한 CSS 선택자를 사용하세요.
//            // 여기서는 예시로 적합한 선택자를 찾아야 합니다.
//            List<HtmlElement> items = page.getByXPath("#section_list > div > ul > li:nth-child(1) > div > div:nth-child(3) > div.c-card-item__info-top > dl > div.c-card-item__name > dd"); // XPath를 사용한 예
//
//            for (HtmlElement item : items) {
////                results.add(item.getTextContent());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        return results;
    }
}
