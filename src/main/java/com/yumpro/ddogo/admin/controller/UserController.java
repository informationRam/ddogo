package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.admin.entity.User;
import com.yumpro.ddogo.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;
    @GetMapping("/user/list")
    public String userList(Model model, @RequestParam(value = "page", defaultValue = "0") int page){
        //1.get Param
        //2.business logic
        Page<User> questionPage = this.userService.getList(page);
        //3.model
        model.addAttribute("questionPage",questionPage);
        //4.view
        return "admin/user_list";
    }
}