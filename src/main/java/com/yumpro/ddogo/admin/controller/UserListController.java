package com.yumpro.ddogo.admin.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.admin.service.UserListService;
import com.yumpro.ddogo.admin.validation.UserModiForm;
import com.yumpro.ddogo.common.entity.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserListController {
    private final UserListService userService;

    //리스트 보여주기(검색 정렬 페이지네이션)
    @PreAuthorize("isAuthenticated()")
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

    //수정폼 보여주기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/modify/{userNo}")
    public String detail(@PathVariable("userNo") Integer userNo, Principal principal, Model model){
        //1.파라미터받기
        //2.비즈니스로직수행
        User user =userService.getUser(userNo);
        UserModiForm userModiForm=userService.toModifyForm(user);

        if ( !principal.getName().equals("admin") ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        //3.Model
        model.addAttribute("userModiForm", userModiForm);
        //4.View
        return "admin/user_modify_admin";
    }

    //수정 처리하기
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/modify/{userNo}")
    public String userUpdate(Model model, @Valid UserModiForm userModiForm,
                             BindingResult bindingResult, @PathVariable int userNo) {

        User user = userService.getUser(userNo);
        model.addAttribute("userModiForm", userModiForm);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getModel());
            return "admin/user_modify_admin";
        }
        //이메일 중복여부체크
        if (userService.checkEmailDuplication(user, userModiForm)) {
            bindingResult.rejectValue("email", "EmailInCorrect", "이미 사용중인 이메일 입니다.");
            return "admin/user_modify_admin";
        }

        //아이디 중복여부체크
        if (userService.checkIdDuplication(user, userModiForm)) {
            bindingResult.rejectValue("user_id", "User_idInCorrect", "이미 사용중인 아이디 입니다.");
            return "admin/user_modify_admin";
        }

        try {
            userService.userModify(user, userModiForm);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("modifyFailed", "수정에 실패했습니다. 오류가 계속되면 관리자에게 문의해주세요");
            model.addAttribute("userModiForm", userModiForm);
            return "admin/user_modify_admin";
        }

        return "redirect:/admin/user/list";
    }

    //강퇴 전 관리자 비번인증
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/valiAdmin")
    public ResponseEntity<String> validateAdmin(@RequestBody String body) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);
            String inputPassword = jsonNode.get("inputPassword").asText();

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            boolean passwordMatches = encoder.matches(inputPassword, userService.getUser(104).getPwd());

            if (passwordMatches) {
                return ResponseEntity.ok("Valid admin password");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호가 일치하지 않습니다");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원 삭제에 실패했습니다");
        }
    }

    //메일용 html파일 문서화
    public String readHTMLFileAsString(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            content = "HTML 파일을 불러오지 못했습니다.";
        }
        return content;
    }

    //회원 강퇴(+메일발송)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/user/delete/{username}")
    public ResponseEntity<String> expelUser(@PathVariable String username) {
        Optional<User> userOptional = userService.findUserByUserId(username);
        if (userOptional.isPresent()) {
            String toEmail = userOptional.get().getEmail();
            String subject = "[또갈지도]회원 강제 탈퇴 안내";

            // HTML 파일을 문자열로 읽어옵니다.
            String text = readHTMLFileAsString("src/main/resources/templates/admin/user_delete.html");

            userService.deleteUser(userOptional.get());
            userService.sendSimpleEmail(toEmail, subject, text);

            return ResponseEntity.ok("회원정보를 성공적으로 삭제했습니다");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("수정중이던 정보를 마무리해주세요");
        }
    }
}