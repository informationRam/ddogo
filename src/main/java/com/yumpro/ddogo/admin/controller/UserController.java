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
    public String userList(Model model, @RequestParam Map<String, Object> map, @RequestParam(value = "page", defaultValue = "1") int currentPage) {

        int limit = 10; // 페이지당 보여줄 아이템 개수
        int offset = (currentPage - 1) * limit;

        map.put("limit", limit);
        map.put("offset", offset);

        List<UserDTO> userList = userService.userList(map);

        int totalPages = (int) Math.ceil((double) userList.size() / limit);

        model.addAttribute("users", userList);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);

        return "admin/user_list";
    }
}