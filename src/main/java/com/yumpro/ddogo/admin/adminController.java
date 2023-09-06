package com.yumpro.ddogo.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class adminController {

    @GetMapping("/go")
    public String ss(Principal principal){
        if(principal.getName().equals("admin")){
            return "/admin2/adminForm";
        }else {
            return "/common/ddoError";
        }
    }
}
