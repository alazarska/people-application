package com.alazarska.peopleapplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String redirectHomePage() {
        return "redirect:/people";
    }
}
