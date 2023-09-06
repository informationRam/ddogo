package com.yumpro.ddogo.user.controller;


import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.kakao.entity.KakaoAccount;
import com.yumpro.ddogo.kakao.entity.KakaoData;
import com.yumpro.ddogo.kakao.entity.Kakaouser;
import com.yumpro.ddogo.kakao.service.Kakaoservice;
import com.yumpro.ddogo.mail.service.EmailService;

import com.yumpro.ddogo.user.service.UserService;
import com.yumpro.ddogo.user.validation.LoginVaildation;
import com.yumpro.ddogo.user.validation.UserCreateForm;
import com.yumpro.ddogo.user.validation.UserModifyForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


//시큐리티 저장 값 가져오기 세션대용
   /* User user = userService.getUser(principal.getName());
        model.addAttribute("user", user.get().getUser_no());        ==> 유저의 no를 가져옴    */

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    // 메일발송 서비스
    private final EmailService emailService;



    // 카카오 서비스
    private final Kakaoservice kakaoservice;

    //회원가입 폼
    @GetMapping("/joinForm")
    public String joinForm(UserCreateForm userCreateForm, Model model) {
        model.addAttribute("userCreateForm", userCreateForm);
        return "/user/joinForm";
    }

 //회원가입처리 후 로그인페이지로 이동
    @PostMapping("/join")
    public String userJoin(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/joinForm";
        }

        // 아이디 중복 여부체크
        if (userService.checkUserIdDuplication(userCreateForm.getUser_id())) {
            bindingResult.rejectValue("user_id", "User_idInCorrect", "이미 사용중인 아이디입니다.");
            return "user/joinForm";
        }

        // 이메일 중복 여부체크
        if (userService.checkEmailDuplication(userCreateForm.getEmail())) {
            bindingResult.rejectValue("email", "EmailInCorrect", "이미 사용중인 이메일 입니다.");
            return "user/joinForm";
        }

        // 비밀번호, 비밀번호 확인 동일 체크
        if (!userCreateForm.getPwd1().equals(userCreateForm.getPwd2())) {
            bindingResult.rejectValue("pwd2", "pwdInCorrect", "비밀번호 확인 값이 다릅니다.");
            return "user/joinForm";
        } else {
            userService.userJoin(userCreateForm);
            return "redirect:/user/login";
        }
    }

    //로그인 화면(get)
    @GetMapping("/login")
    public String loginForm(LoginVaildation loginVaildation,Model model,UserCreateForm userCreateForm) {
        model.addAttribute("userCreateForm",userCreateForm);
        return "/user/loginForm";
    }

    //로그인 처리
    @PostMapping("/login")
    public String login(@Valid LoginVaildation loginVaildation, BindingResult bindingResult, Model model, HttpSession session, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        } else {
            return "redirect:/";
        }
    }

    //id 찾기 form
    @GetMapping("/searchidform")
    public String searchidform(){
        return "/user/searchid_Form";
    }

    //id 찾기 GET 요청
    @GetMapping("/searchid")
    @ResponseBody
    public Map<String, String> getsearchid(@RequestParam("email") String email) {
        Map<String, String> user = new HashMap<>();
        String userid = userService.searchId(email);
        //회원정보에 사용자가 입력한 이메일이 있는지 확인
        if (!userService.checkEmailDuplication(email) || userid == null) {
            user.put("userid", "회원정보를 찾을 수 없습니다.");
            return user;
        } else {
            user.put("userid", userid);
            return user;
        }
    }

    //id 찾기 POST 요청
    @PostMapping("/searchid")
    @ResponseBody
    public Map<String, String> postsearchid(@RequestParam("email") String email) {
        Map<String, String> user = new HashMap<>();
        String userid = userService.searchId(email);
        //회원정보에 사용자가 입력한 이메일이 있는지 확인
        if (!userService.checkEmailDuplication(email) || userid == null) {
            user.put("userid", "회원정보를 찾을 수 없습니다.");
            return user;
        } else {
            user.put("userid", userid);
            return user;
        }
    }

    // 비밀번호 찾기 폼 pwdsearch_Form
    @GetMapping("/pwdsearchfrom")
    public String pwdsearchForm(){
        return "/user/pwdsearch_Form";
    }

    // 비밀번호 찾기
    @PostMapping("/pwdsearch")
    @ResponseBody
    public Map<String, String> pwdsearch(@RequestParam("email") String email,@RequestParam("user_id") String user_id) {
        System.out.println("email"+email);
        System.out.println("user_id"+user_id);
        Map<String, String> message = new HashMap<>();

        // 이메일 중복 여부 체크
        if (userService.pwdsearch(user_id,email)) {
            message.put("message","사용자 정보를 찾을 수 없습니다.");
            return message;
        } else {
            String tempPassword = emailService.sendSimpleMessage(email);   // 메일 발송 후 임시 비밀번호 값 저장
            User user = userService.getUser(user_id);
            userService.userpwdModify(user, tempPassword);                // 임시 패스워드로 변경
            message.put("message","메일을 발송하였습니다.");
            return message; // 메일 전송 성공 -> 로그인 창으로 이동
        }
    }
    //정보 수정 폼
    @GetMapping("/modifyForm/{user_id}")
    public String userUpdateForm(Principal principal, Model model) {
        User user =  userService.getUser(principal.getName());
        UserModifyForm userModifyForm = userService.touserModifyForm(user); // 인증을위한 userModifyForm값으로 변경
        model.addAttribute("userModifyForm", userModifyForm);
        return "/user/userModifyForm";
    }

    // 정보수정 실행
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{user_id}")
    public String userUpdate(Model model, @Valid UserModifyForm userModifyForm,
                             BindingResult bindingResult, Principal principal) {

        User user = userService.getUser(principal.getName());
        model.addAttribute("userModifyForm",userModifyForm);

        if (bindingResult.hasErrors()) {
            return "/user/userModifyForm";
        }
        //이메일 중복여부체크
        if (userService.checkEmailDuplication(user,userModifyForm)) {
            bindingResult.rejectValue("email", "EmailInCorrect", "이미 사용중인 이메일 입니다.");
            return "user/userModifyForm";
        }
        //비밀번호, 비밀번호 확인 동일 체크
        if (!userModifyForm.getPwd1().equals(userModifyForm.getPwd2())) {
            bindingResult.rejectValue("pwd2", "pwdInCorrect", "비밀번호와 비밀번호확인이 불일치합니다.");
            return "/user/userModifyForm";
        }try{
            userService.userModify(user,userModifyForm);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            bindingResult.reject("modifyFailed","정보를 확인해주세요.");
            return "/user/userModifyForm";
        }
        return "redirect:/";
    }

    //  탈퇴하기
    @GetMapping("/delete/{user_id}")
    public String questionDelete(@PathVariable("user_id") String user_id,
                                 Principal principal){
        User user = userService.getUser(user_id); //회원상세
        if(!user.getUserId().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제권한이 없습니다.");
        }
        userService.userDelete(user);
        return "redirect:/user/logout";    //목록으로이동
    }

// ----------------- 카카오 테스트 중 ------------------

    //카카오 테스트폼
    @GetMapping("/kakao2")
    public String kakao() {
        return "/user/kakao2";
    }




    //카카오 로그인
    @PostMapping("/kakao")
    public String kakaoLogin(@RequestBody KakaoData kakaoData) throws ParseException, java.text.ParseException {
        Kakaouser user;
        // 카카오 인증 정보를 사용하여 사용자 인증 처리
        UserDetails userDetails = userService.loadUserByKakaoToken(kakaoData.getAuthObj().getAccess_token());

        // 사용자 인증 성공 시 Spring Security의 Authentication에 사용자 정보 설정
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Now you can access the data in the KakaoData object
        System.out.println("Access Token: " + kakaoData.getAuthObj().getAccess_token());
        System.out.println("Expires In: " + kakaoData.getAuthObj().getExpires_in());
        String accessToken = kakaoData.getAuthObj().getAccess_token();

        // Access Kakao Account data
        KakaoAccount kakaoAccount = kakaoData.getKakao_account();
        System.out.println("Age Range: " + kakaoAccount.getAge_range());
        System.out.println("Birthday: " + kakaoAccount.getBirthday());
        System.out.println("Email: " + kakaoAccount.getEmail());
        System.out.println("Gender: " + kakaoAccount.getGender());
        String email = kakaoAccount.getEmail();

        // 사용자 정보 조회
        user = kakaoservice.getUser(accessToken);

        if (user != null) {
            // 사용자가 이미 존재하는 경우 중복 처리를 할 수 있음
            if (email.equals(user.getEmail())) {
                return "/user/kalogin";
            } else {
                // 중복값이 없으면 DB에 저장한다.
                kakaoservice.add(kakaoData.getAuthObj().getAccess_token(), kakaoAccount);
                userService.kakaoJoin(kakaoData.getAuthObj().getAccess_token(), kakaoAccount);
                return "/user/kalogin";
            }
        } else {
            // 사용자가 없는 경우 DB에 등록한다.
            kakaoservice.add(kakaoData.getAuthObj().getAccess_token(), kakaoAccount);
            userService.kakaoJoin(kakaoData.getAuthObj().getAccess_token(), kakaoAccount);
            return "/user/kalogin";
        }
    }



    @RequestMapping("/kalogin")
    public String authtest(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {

        System.out.println("MDTO : "+principalDetails.getMdto());
        model.addAttribute("user", principalDetails.getMdto());
        return "login/authtest";
    }
    /*
    @PostMapping("/kakao")
    public String kakaoLogin(@RequestBody KakaoAccount kakaoAccount) {
        System.out.println("받음!@");

        System.out.println("kakaoAccount:"+kakaoAccount);


        *//*kakaoAccount = kakaoUser.getKakao_account();*//*
     *//*   if(kakaoAccount != null){
            userService.kakaoJoin(kakaoAccount);
        }*//*
        String profileNickname = kakaoAccount.getProfileNickname();
        String gender = kakaoAccount.getGender();
        String ageRange = kakaoAccount.getAgeRange();
        String birthday = kakaoAccount.getBirthday();
        String email = kakaoAccount.getEmail();

        // 사용자 정보를 출력하거나 원하는 작업을 수행합니다.
        System.out.println("Profile Nickname: " + profileNickname);
        System.out.println("Gender: " + gender);
        System.out.println("Age Range: " + ageRange);
        System.out.println("Birthday: " + birthday);
        System.out.println("email: " + email);


        // 원하는 로직을 수행한 후 응답을 반환합니다.
        return "카카오 로그인 완료";
    }*/


/*



    @PostMapping("/kakao")
    public String kakaoLogin(@RequestBody KakaoAccount kakaoAccount,@RequestBody Map<String, String> data) {
        System.out.println("받음!@");

        System.out.println("kakaoAccount:"+kakaoAccount);
        String nickname = data.get("nickname");

        */
/*  kakaoAccount = kakaoUser.getKakao_account();*//*

        if(kakaoAccount != null){
            userService.kakaoJoin(kakaoAccount);
        }
        String profileNickname = kakaoAccount.getProfileNickname();
        String gender = kakaoAccount.getGender();
        String ageRange = kakaoAccount.getAgeRange();
        String birthday = kakaoAccount.getBirthday();
        String email = kakaoAccount.getEmail();

        // 사용자 정보를 출력하거나 원하는 작업을 수행합니다.
        System.out.println("Profile Nickname: " + profileNickname);
        System.out.println("Gender: " + gender);
        System.out.println("Age Range: " + ageRange);
        System.out.println("Birthday: " + birthday);
        System.out.println("email: " + email);
        System.out.println("nickname:" +nickname);

        // 원하는 로직을 수행한 후 응답을 반환합니다.
        return "카카오 로그인 완료";
    }

*/


    //카카오 테스트폼
    @GetMapping("/kakaoout")
    public String kakao2() {
        return "/user/kakaologout";
    }
}


