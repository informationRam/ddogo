package com.yumpro.ddogo.main;

import com.yumpro.ddogo.user.controller.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {

   /* @GetMapping("/")
    public String home(Model model, HttpSession session) {
        //세션가져오는법
        String foundUserId = (String) session.getAttribute("foundUserId");
        model.addAttribute("foundUserId", foundUserId);
        return "/index";
    }*/


/*    @GetMapping("/")
    public String userUpdateForm(@AuthenticationPrincipal CustomUserDetails userDetails, Model model, Principal principal) {
        String user_id = userDetails.getUser_id();
        model.addAttribute("user_id", user_id);
        return "/user/userModifyForm";
    }*/
}
