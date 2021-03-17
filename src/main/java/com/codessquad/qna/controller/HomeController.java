package com.codessquad.qna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectQuestions() {
        return "redirect:/questions";
    }
}
