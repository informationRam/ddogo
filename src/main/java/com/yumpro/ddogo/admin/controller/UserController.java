package com.yumpro.ddogo.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {
    @GetMapping("/user/list")
    public String userList(){
        //1.get Param

        //2.business logic

        //3.model

        //4.view
        return "admin/user_list";
    }
}
