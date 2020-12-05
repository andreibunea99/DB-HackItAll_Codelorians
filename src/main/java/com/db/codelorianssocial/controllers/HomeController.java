package com.db.codelorianssocial.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    boolean hidden = true;

    @GetMapping("/")
    public String mainPage(Model model) {
        model.addAttribute("isHidden", hidden);
        return "index";
    }

    @GetMapping("/codelorians-discord")
    public String discordPage(Model model) {
        hidden = !hidden;

        System.out.println(hidden);

//        model.addAttribute("isHidden", hidden);
        return mainPage(model);
    }
}
