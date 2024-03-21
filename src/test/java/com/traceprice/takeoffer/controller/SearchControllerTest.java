package com.traceprice.takeoffer.controller;

import com.traceprice.takeoffer.service.CrawlerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrawlerService coupangCrawlerService;

    @MockBean
    private CrawlerService gmarketCrawlerService;

    @Test
    public void searchTest() throws Exception {

    }
}
