package com.traceprice.takeoffer.controller;

import com.traceprice.takeoffer.dto.Product;
import com.traceprice.takeoffer.service.CrawlerService;
import com.traceprice.takeoffer.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    SearchService searchService;

    @GetMapping("/")
    public String home(Model m){
        List<Product> home = searchService.homeSearch();
        List<Product> apple = searchService.appleSearch();
        m.addAttribute("coupangProducts", (home != null && !home.isEmpty()) ? home : null);
        m.addAttribute("appleProducts", (apple != null && !apple.isEmpty()) ? apple : null);
//
//        if(apple.size() !=0 && apple != null)
//            m.addAttribute("appleProducts", apple);
//        else
//            m.addAttribute("appleProducts", null);
        return "home";
    }

    
    @GetMapping("/crawler")
    public String crawler(){ return "crawlerdisplay"; }

    @GetMapping("/apple")
    public String apple(){ return "appledisplay"; }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model m,
                         @RequestParam(required = false, defaultValue = "0") String options,
                         @PageableDefault(size = 36, page = 0) Pageable pageable) {
        m.addAttribute("search", query);
        m.addAttribute("coupangProducts",searchService.search(query, options, pageable));

        return "searchPage";
    }
}
