package com.traceprice.takeoffer.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @GetMapping("/bbb")
    public String setSessionAttribute(HttpSession session, Model m) {
        session.setAttribute("user", "joe");
        m.addAttribute("onevar", "one");
        m.addAttribute("twovar", "two");
        m.addAttribute("threevar", "thr");
        m.addAttribute("count", 2);
        Date d = new Date();
        m.addAttribute("date", d.getTime());
        m.addAttribute("true", true);
        m.addAttribute("selectedOption", "option2");
        m.addAttribute("option",null);
        System.out.println("gd");

        List<String> l = new ArrayList<>();
        l.add("A");
        l.add("c");
        m.addAttribute("t","이름");
        m.addAttribute("c","목록");
        m.addAttribute("i",l);
        return "layout/aa";
    }

}
