package com.yumpro.ddogo.user.controller;

import com.yumpro.ddogo.mail.service.EmailService;
import com.yumpro.ddogo.user.DTO.UserDTO;
import com.yumpro.ddogo.user.entity.User;
import com.yumpro.ddogo.user.exception.DuplicateUserIdException;
import com.yumpro.ddogo.user.service.UserSecurityService;
import com.yumpro.ddogo.user.service.UserService;
import com.yumpro.ddogo.user.validation.LoginVaildation;
import com.yumpro.ddogo.user.validation.UserCreateForm;
import com.yumpro.ddogo.user.validation.UserModifyForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;


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



   /* @PostMapping("/join")
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
            bindingResult.rejectValue("pwd2", "pwdInCorrect", "비밀번호확인 값이 다릅니다.");
            return "user/joinForm";
        } else {
            userService.userJoin(userCreateForm);
            return "redirect:/user/login";
        }
    }*/

    //로그인 화면(get)
    @GetMapping("/login")
    public String loginForm(LoginVaildation loginVaildation) {
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

    //id찾기 폼
    @GetMapping("/searchid")
    public String searchidForm(UserDTO userDTO, Model model) {
        model.addAttribute("userDTO", userDTO);
        return "/user/searchid_Form";
    }

    /*
        @PostMapping("/searchid")
        public String searchId(Model model, @ModelAttribute("userDTO") UserDTO userDTO, HttpSession session) {
            User user = userService.toEntity(userDTO);
            String user_id = userService.searchId(user.getEmail());

            if (user_id != null && !user_id.isEmpty()) {
                //세션으로 값 담기
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
// 아이디 찾기
    @GetMapping("/searchid2")
    public Map<String, String> searchId(@ModelAttribute("userDTO") UserDTO userDTO, Model model, BindingResult bindingResult) {
        Map<String, String> response = new HashMap<>();
        System.out.println("userDTO.getEmail():"+userDTO.getEmail());

        try {
            //회원정보에 사용자가 입력한 이메일이 있는지 확인
            if (!userService.checkEmailDuplication(userDTO.getEmail())) {
                bindingResult.rejectValue("email", "EmailInCorrect", "사용자 정보를 찾을 수 없습니다.");
                response.put("user_id", "사용자 정보를 찾을 수 없습니다.");
                return response;
            } else {
                // 회원정보 일치시 아이디값 전달
                String user_id = userService.searchId(userDTO.getEmail());
                System.out.println("user_id:"+user_id);
                response.put("user_id", user_id);
                model.addAttribute("user_id", user_id);
                return response;
            }
        } catch (Exception e) {
                return response;
        }
    }

    //test
   /* @GetMapping("/searchid3")
    @ResponseBody
    public void jsonTest() throws ParseException {

        //1. Json 문자열
        String strJson = "{\"userId\":\"sim\", "
                + "\"userPw\":\"simpw\","
                + "\"userInfo\":{"
                + "\"age\":50,"
                + "\"sex\":\"male\""
                + "}"
                + "}";

        //2. Parser
        JSONParser jsonParser = new JSONParser();

        //3. To Object
        Object obj = jsonParser.parse(strJson);

        //4. To JsonObject
        JSONObject jsonObj = (JSONObject) obj;

        //print
        System.out.println(jsonObj.get("userId")); //sim
        System.out.println(jsonObj.get("userPw")); //simpw
        System.out.println(jsonObj.get("userInfo")); // {"sex":"male","age":50}
    }
*/



/*
    @PostMapping("/searchid")
    public String searchId(@ModelAttribute("userDTO") UserDTO userDTO,Model model, BindingResult bindingResult) {

        System.out.println("userD:"+userDTO.getEmail());
        //회원정보에 사용자가 입력한 이메일이 있는지 확인
        if (!userService.checkEmailDuplication2(userDTO.getEmail())) {
            bindingResult.rejectValue("email", "EmailInCorrect", "사용자 정보를 찾을 수 없습니다.");
            return "user/searchid_Form";
        }else {
            // 회원정보 일치시 아이디값 전달
            String user_id = userService.searchId(userDTO.getEmail());
            System.out.println("user_id:"+user_id);
            model.addAttribute("user_id",user_id);
            return "/user/id";
        }
    }*/

    // 비밀번호 찾기 폼 pwdsearch_Form
    @GetMapping("/pwdsearch")
    public String pwdsearchForm(UserDTO userDTO, Model model){
        model.addAttribute("userDTO",userDTO);
        return "/user/pwdsearch_Form";
    }

    // 비밀번호 찾기
    @PostMapping("/pwdsearch")
    public String pwdsearch(UserDTO userDTO, Model model, BindingResult bindingResult) {
            model.addAttribute("userDTO", userDTO);

            // 이메일 중복 여부 체크
            if (userService.pwdsearch(userDTO)) {
                bindingResult.rejectValue("email", "EmailInCorrect", "사용자 정보를 찾을 수 없습니다.");
                return "user/pwdsearch_Form";
            } else {
                String tempPassword = emailService.sendSimpleMessage(userDTO.getEmail());   // 메일 발송 후 임시 비밀번호 값 저장
                User user = userService.getUser(userDTO.getUser_id());
                userService.userpwdModify(user, tempPassword);                // 임시 패스워드로 변경

                // 메일 발송 성공 메시지를 Model에 추가
                model.addAttribute("mailSentMessage", "메일을 발송했습니다. 확인을 누르면 다음 단계로 진행됩니다.");
                return "user/loginForm"; // 메일 전송 성공 -> 로그인 창으로 이동
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

}
