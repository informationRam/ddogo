package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.service.ImsiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class DashboardController {
    private ImsiService imsiService;
    @GetMapping("/admin")
    public String showMap(){
        int i =imsiService.getTotalMemberCNT();
        System.out.println(i);
        return "admin/dashboard";
    }
}
