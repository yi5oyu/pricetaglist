package com.traceprice.takeoffer.controller;

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

@Controller
public class ViewController {

    @Autowired
    SearchService searchService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/gg")
    public String b(){
        return "main/gg";
    }

    @GetMapping("/dg")
    public String a(){
        return "/dg";
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
        if(!query.isEmpty()){
            m.addAttribute("coupangProducts",searchService.Search(query, options, pageable));
        }else{
            m.addAttribute("coupangProducts",null);
        }

        return "searchPage";
    }
}
