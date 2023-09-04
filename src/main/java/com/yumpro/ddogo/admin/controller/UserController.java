package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserController {
    private final UserService userService;
    @GetMapping("/user/list")
    public String userList(Model model, @RequestParam Map<String, Object> map) {
        //1.get Param
        //2.business logic
        List<UserDTO> userList=userService.userList(map);
        //3.model
        model.addAttribute("users", userList);
        //4.view
        return "admin/user_list";
    }
}