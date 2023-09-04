package com.yumpro.ddogo.admin.controller;

import com.yumpro.ddogo.admin.domain.UserDTO;
import com.yumpro.ddogo.admin.service.UserListService;
import com.yumpro.ddogo.admin.validation.UserModiAdmin;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.user.validation.UserModifyForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class UserListController {
    private final UserListService userService;

    //리스트 보여주기(검색 정렬 페이지네이션)
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
    @GetMapping("/user/modify/{userNo}")
    public String detail(@PathVariable("userNo") Integer userNo, Principal principal, Model model){
        //1.파라미터받기
        //2.비즈니스로직수행
        User user =userService.getUser(userNo);
        UserDTO userDTO = userService.toDTO(user);
        System.out.println(userDTO);
        /*
        if ( !principal.getName().equals("admin") ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정권한이 없습니다.");
        }
        */
        /*
        userModiFrom.setUser_no(userDTO.getUser_no());
        userModiFrom.setUser_id(userDTO.getUser_id());
        userModiFrom.setUser_name(userDTO.getUser_name());
        userModiFrom.setGender(userDTO.getGender());
        userModiFrom.setEmail(userDTO.getEmail());
        userModiFrom.setJoinDate(userDTO.getJoin_date());
        */
        //3.Model
        model.addAttribute("userModiForm", userDTO);

        //4.View
        return "admin/user_modify_admin"; //templates폴더하위  question_detail.html
    }

    //수정 처리하기
    @PostMapping("/user/modify/{userNo}")
    public String userUpdate(Model model, @Valid UserModiAdmin userModiForm,
                             BindingResult bindingResult) {

        model.addAttribute("userModiForm",userModiForm);

        //이메일 중복여부체크
        //비밀번호, 비밀번호 확인 동일 체크
        return "redirect:/admin/user/list";
    }
}