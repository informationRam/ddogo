package com.yumpro.ddogo.test;

import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;

  /*  @PreAuthorize("isAuthenticated()")*/
    @GetMapping("/go")
    public String tt(){
        return "/user/jsontest";
    }



    //ajax test!!!!!!

    // GET 요청을 처리하는 부분은 유지합니다.
    @GetMapping("/goajax")
    @ResponseBody
    public Map<String, String> process() {
        Map<String, String> data = new HashMap<>();
        data.put("name", "ryu");
        data.put("age", "10");
        return data;
    }


    // POST 요청을 처리하는 부분입니다.
    @PostMapping("/goajax")
    @ResponseBody
    public Map<String, String> goAjax(@RequestParam("email") String email) {
        System.out.println("email:" + email);
        String userid = userService.searchId(email);
        System.out.println("userid:" + userid);

        Map<String, String> user = new HashMap<>();
        user.put("userid", userid);

        return user;
    }
}



