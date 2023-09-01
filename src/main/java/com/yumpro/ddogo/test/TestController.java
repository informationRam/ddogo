package com.yumpro.ddogo.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/go")
    public String tt(){
        return "/test/test/";
    }
}
