package com.yumpro.ddogo.main;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        //세션가져오는법
        String foundUserId = (String) session.getAttribute("foundUserId");
        model.addAttribute("foundUserId", foundUserId);
        return "/index";
    }
}
