package com.yumpro.ddogo.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.mail.service.EmailService;

import com.yumpro.ddogo.user.DTO.KakaoProfile;
import com.yumpro.ddogo.user.DTO.OAuthToken;
import com.yumpro.ddogo.user.service.UserService;
import com.yumpro.ddogo.user.validation.LoginVaildation;
import com.yumpro.ddogo.user.validation.UserCreateForm;
import com.yumpro.ddogo.user.validation.UserModifyForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


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
    //세션값 담기(1)
    private final HttpSession session;

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
            return "/user/loginForm";
        }

        // 아이디 중복 여부체크
        if (userService.checkUserIdDuplication(userCreateForm.getUser_id())) {
            bindingResult.rejectValue("user_id", "User_idInCorrect", "이미 사용중인 아이디입니다.");
            return "/user/loginForm";
        }

        // 이메일 중복 여부체크
        if (userService.checkEmailDuplication(userCreateForm.getEmail())) {
            bindingResult.rejectValue("email", "EmailInCorrect", "이미 사용중인 이메일 입니다.");
            return "/user/loginForm";
        }

        // 비밀번호, 비밀번호 확인 동일 체크
        if (!userCreateForm.getPwd1().equals(userCreateForm.getPwd2())) {
            bindingResult.rejectValue("pwd2", "pwdInCorrect", "비밀번호 확인 값이 다릅니다.");
            return "/user/loginForm";
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
        System.out.println("?durlf?");
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

    //카카오 로그인 --------------------- 컨트롤러

    /*
    * Content-type: application/x-www-form-urlencoded;charset=utf-8
    https://kauth.kakao.com/oauth/token
    grant_type=authorization_code
    client_id=5a378555d1b9b81713af9609ce071c9d
    redirect_uri=http://localhost/user/kakao
    * */

    @GetMapping("/kakaoform")
    public String callback(){
        return "/user/kakaoLogin";
    }

    //카카오 로그인
    @GetMapping("/kakao")
    public String callback(String code) throws JsonProcessingException, ParseException, java.text.ParseException {
        // 1. code 값 존재 유무 확인
        if(code == null || code.isEmpty()){
            return "redirect:/loginForm";
        }

        RestTemplate rt = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        // 2. code 값 카카오 전달 -> access token 받기
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "5a378555d1b9b81713af9609ce071c9d");
        params.add("redirect_uri", "http://localhost/user/kakao"); // 2차 검증
        params.add("code", code); // 핵심

        HttpEntity<MultiValueMap<String,String>> KakaoTokenRequest =
                new HttpEntity<>(params,httpHeaders);
               /* Fetch.kakao("https://kauth.kakao.com/oauth/token", HttpMethod.POST, body);*/

        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                KakaoTokenRequest,
                String.class
        );

        // 3. access token으로 카카오의 홍길동 resource 접근 가능해짐 -> access token을 파싱하고
        /*ObjectMapper om = new ObjectMapper();*/
        ObjectMapper om = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
            oAuthToken = om.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("카카오 엑세스 토큰:" +oAuthToken.getAccess_token());

        //2.
        RestTemplate rt2 = new RestTemplate();
        HttpHeaders httpHeaders2 = new HttpHeaders();
        httpHeaders2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
        httpHeaders2.add("Authorization","Bearer "+oAuthToken.getAccess_token());

        // 2. code 값 카카오 전달 -> access token 받기

        HttpEntity<MultiValueMap<String,String>> KakaoProfileRequest2 =
                new HttpEntity<>(httpHeaders2);

        ResponseEntity<String> response2 = rt2.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                KakaoProfileRequest2,
                String.class
        );

        // 3. access token으로 카카오의 홍길동 resource 접근 가능해짐 -> access token을 파싱하고
        ObjectMapper om2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = om2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



        System.out.println("카카오 아이디: "+kakaoProfile.getId());
        System.out.println("카카오 이메일: "+kakaoProfile.getKakaoAccount().getEmail());
        System.out.println("카카오 유저네임: "+kakaoProfile.getKakaoAccount().getEmail());

        String email = kakaoProfile.getKakaoAccount().getEmail();

        // 5. 해당 provider_id 값으로 회원가입되어 있는 user의 username 정보가 있는지 DB 조회 (X)
        User kakaouser = userService.getUser2(email);

        // 6. 있으면 그 user 정보로 session 만들어주고, (자동로그인) (X)
        if(kakaouser != null){
            System.out.println("디버그 : 회원정보가 있어서 로그인을 바로 진행합니다");
            session.setAttribute("principal", kakaouser);
        }

        // 7. 없으면 강제 회원가입 시키고, 그 정보로 session 만들어주고, (자동로그인)
        if(kakaouser == null){
            System.out.println("디버그 : 회원정보가 없어서 회원가입 후 로그인을 바로 진행합니다");
            userService.kakaoJoin(kakaoProfile);
            User newKakaouser = userService.getUser2(email);
            session.setAttribute("principal", newKakaouser);
        }

        return "redirect:/";
    }
}


