package com.yumpro.ddogo.user.controller;

import com.yumpro.ddogo.mail.service.EmailService;
import com.yumpro.ddogo.user.DTO.UserDTO;
import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.service.UserSecurityService;
import com.yumpro.ddogo.user.service.UserService;
import com.yumpro.ddogo.user.validation.LoginVaildation;
import com.yumpro.ddogo.user.validation.UserCreateForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

    //시큐리티 저장 값 가져오기 세션대용
   /* Optional<User> user = userService.getUser(principal.getName());
        model.addAttribute("user", user.get().getUser_no());        ==> 유저의 no를 가져옴    */

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 메일발송 서비스
    private final EmailService emailService;
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
    public String login(@Valid LoginVaildation loginVaildation, BindingResult bindingResult, Model model,HttpSession session,Principal principal) {
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }else {
            User user = userService.getUser(principal.getName());
            System.out.println("login-user:"+user);
            session.setAttribute("user",user);   //로그인성공시 값을 세션에 저장
            return "redirect:/";
        }
    }

    //id찾기 폼
    @GetMapping("/searchid")
    public String searchidForm(UserDTO userDTO,Model model){
        model.addAttribute("userDTO",userDTO);
        return "/user/searchid_Form";
    }

/*
    @PostMapping("/searchid")
    public String searchId(Model model, @ModelAttribute("userDTO") UserDTO userDTO, HttpSession session) {
        User user = userService.toEntity(userDTO);
        String user_id = userService.searchId(user.getEmail());

        if (user_id != null && !user_id.isEmpty()) {
            //세션으로 값 담기
           */
/* session.setAttribute("foundUserId", userId);
           * String foundUserId = (String) session.getAttribute("foundUserId");*//*


          model.addAttribute("user_id",user_id);

        return "/user/id"; // 아이디를 찾았을경우 아이디를 보여주는 폼으로 이동
        } else {
            model.addAttribute("user_id", "N");
            return "user/searchid_Form"; // 사용자를 찾지 못했을 경우 다시 아이디 찾기 폼으로
        }
    }
*/

    @PostMapping("/searchid")
    public ResponseEntity<?> searchId(@ModelAttribute("userDTO") UserDTO userDTO) {
        User user = userService.toEntity(userDTO);
        String user_id = userService.searchId(user.getEmail());

        if (user_id != null && !user_id.isEmpty()) {
            return ResponseEntity.ok(user_id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("id")
    public String id(Model model, User user, @RequestParam String user_id) {
        model.addAttribute("user_id",user_id);
        return "/user/id";
    }

    //비밀번호 찾기 폼 pwdsearch_Form
    @GetMapping("/pwdsearch")
    public String pwdsearchForm(UserDTO userDTO, Model model){
        model.addAttribute("userDTO",userDTO);
        return "/user/pwdsearch_Form";
    }

    // 비밀번호 찾기
    @PostMapping("/pwdsearch")
    public String pwdsearch(UserDTO userDTO, Model model,HttpSession session){
        model.addAttribute("userDTO",userDTO);
        User user = userService.toEntity(userDTO);
        System.out.println("userService.pwdsearch(user)"+userService.pwdsearch(user));
        if(userService.pwdsearch(user)){
            session.setAttribute("user_id",user.getUserId());      //user_id :세션에 값저장
            String tempPassword = emailService.sendSimpleMessage(user.getEmail());
            session.setAttribute("tempPassword",tempPassword);      //tempPassword :세션에 값저장
            System.out.println("tempPassword: "+tempPassword);
            return "/user/pwdCheck";          //메일전송성공
        }else {
            String result = "N";
            return result;
        }
    }

    //인증번호 체크
    @PostMapping("/tempPassword")
    public String tempPassword(@RequestParam String userEnteredPassword,Model model,HttpSession session,Principal principal){
        String tempPassword = (String) session.getAttribute("tempPassword");
        String user_id = (String) session.getAttribute("user_id");
        System.out.println("tempPassword2: "+tempPassword);
        System.out.println("user_id: "+user_id);
        String result = "N";
        if(tempPassword.equals(userEnteredPassword)){           //인증번호가 맞으면
            User user = userService.getUser(user_id);
            userService.userpwdModify(user,tempPassword);
            return "redirect:/";        //임시 번호로 패스워드변경.
        }else {
            return result;          //  창에 그대로 머무른다.
        }
    }

    //정보수정 폼
   /* @GetMapping("/modifyForm/{user_id}")
    public String userUpdateForm(Principal principal, Model model,@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        userCreateForm = userService.toUserCreateForm(principal.getName());
        model.addAttribute("userCreateForm",userCreateForm);
        return "user/userModifyForm";
    }*/

    @GetMapping("/modifyForm/{user_id}")
    public String userUpdateForm(Principal principal, Model model) {
        UserCreateForm userCreateForm = userService.toUserCreateForm(principal.getName());
        model.addAttribute("userCreateForm", userCreateForm);
        return "user/userModifyForm";
    }


    // 정보수정
    @PostMapping("/modify")

    public String userUpdate(Model model, @Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        model.addAttribute("userCreateForm", userCreateForm);
        if (bindingResult.hasErrors()) {
            return "user/userModifyForm";
        }
        //이메일 중복여부체크
        if (userService.checkEmailDuplication(userCreateForm.getEmail())) {
            bindingResult.rejectValue("email", "EmailInCorrect", "이미 사용중인 이메일 입니다.");
            return "user/userModifyForm";
        }

        //비밀번호, 비밀번호 확인 동일 체크
        if (!userCreateForm.getPwd1().equals(userCreateForm.getPwd2())) {
            bindingResult.rejectValue("pwd2", "pwdInCorrect", "비밀번호와 비밀번호확인이 불일치합니다.");
            return "user/userModifyForm";
        }else{
            userService.userModify(userCreateForm,userCreateForm.getEmail(),userCreateForm.getPwd1());
            return "redirect:/";
        }

    }



    // 시큐리티 값 가져오기

    @GetMapping("/dashboard/{user_id}")
    public String dashboard(Model model, Principal principal,@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        model.addAttribute("message", "Hello " + principal.getName());
        model.addAttribute("userCreateForm",userCreateForm);

        userCreateForm = userService.toUserCreateForm(principal.getName());
        model.addAttribute("userCreateForm",userCreateForm);

        return "/user/test";
    }
/*
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal,HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println("dashboard - user="+user);
        model.addAttribute("user", user);
        return "/user/test";
    }*/








}
