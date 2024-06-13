package com.traceprice.takeoffer.controller;

import com.traceprice.takeoffer.dto.Product;
import com.traceprice.takeoffer.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ErrorHandleController implements ErrorController {

    @Autowired
    SearchService searchService;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model m) {
        List<Product> home = searchService.homeSearch();
        List<Product> apple = searchService.appleSearch();
        m.addAttribute("coupangProducts", (home != null && !home.isEmpty()) ? home : null);
        m.addAttribute("appleProducts", (apple != null && !apple.isEmpty()) ? apple : null);
        return "home";
    }

}
