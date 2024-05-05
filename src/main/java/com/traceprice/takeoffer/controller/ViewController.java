package com.traceprice.takeoffer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ViewController {
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
    public  String crawler(){ return "crawlerdisplay"; }

}
