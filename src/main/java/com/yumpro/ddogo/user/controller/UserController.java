package com.yumpro.ddogo.user.controller;

import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.service.UserSecurityService;
import com.yumpro.ddogo.user.service.UserService;
import com.yumpro.ddogo.user.validation.LoginVaildation;
import com.yumpro.ddogo.user.validation.UserCreateForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    //세션값 담기(1)
    private final UserSecurityService userSecurityService;

    //회원가입 폼
    @GetMapping("/joinForm")
    public String joinForm(UserCreateForm userCreateForm, Model model){
        model.addAttribute("userCreateForm",userCreateForm);
        return "/user/joinForm";
    }

    //회원가입처리 후 로그인페이지로 이동
    @PostMapping("/join")
    public String userJoin(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model, LoginVaildation loginVaildation) {
        if (bindingResult.hasErrors()) {
            return "user/joinForm";
        }
        //아이디 중복여부체크
        if (userService.checkUserIdDuplication(userCreateForm.getUser_id())) {
            bindingResult.rejectValue("user_id", "User_idInCorrect", "이미 사용중인 아이디입니다.");
            return "user/joinForm";
        }

        //이메일 중복여부체크
        if (userService.checkEmailDuplication(userCreateForm.getEmail())) {
            bindingResult.rejectValue("email", "EmailInCorrect", "이미 사용중인 이메일 입니다.");
            return "user/joinForm";
        }
        //비밀번호, 비밀번호 확인 동일 체크
        if (!userCreateForm.getPwd1().equals(userCreateForm.getPwd2())) {
            bindingResult.rejectValue("pwd2", "pwdInCorrect", "비밀번호와 비밀번호확인이 불일치합니다.");
            return "user/joinForm";
        }else{
            userService.userJoin(userCreateForm);
            return "redirect:/user/login";
        }

    }

    //로그인 화면(get)
    @GetMapping("/login")
    public String loginForm(LoginVaildation loginVaildation) {
        return "/user/loginForm";
    }


    //로그인 처리
    @PostMapping("/login")
    public String login(@Valid LoginVaildation loginVaildation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }else {
            return "redirect:/";
        }
    }

    //id찾기 폼
    @GetMapping("/searchid")
    public String searchidForm(){
        return "/user/searchid_Form";
    }

    @PostMapping("/searchid")
    public String searchId(Model model, User user) {
        String foundUserId = userService.searchId(user.getEmail());

        if (foundUserId != null) {
            model.addAttribute("userId", foundUserId);
            return "redirect:/"; // 아이디를 찾았을 경우 홈으로 리다이렉트
        } else {
            return "/user/searchid_Form"; // 사용자를 찾지 못했을 경우 다시 아이디 찾기 폼으로
        }
    }

    //비밀번호 찾기 폼 pwdsearch_Form
    @GetMapping("/pwdsearch")
    public String pwdsearchForm(){
        return "/user/pwdsearch_Form";
    }

    @PostMapping("/pwdsearch")
    public String pwdsearch(){
        return "/login";
    }




    // index
  @GetMapping("/")
    public String home(Model model, HttpSession session,@RequestParam(name = "userId", required = false) String userId){
        //세션값을 가져올거야....

      model.addAttribute
              ("userId", userId);

        return "/index";
  }


}
