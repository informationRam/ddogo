package com.yumpro.ddogo.mymap.controller;

import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import com.yumpro.ddogo.mymap.service.MymapService;
import com.yumpro.ddogo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mymap")
public class MymapController {

    private final MymapService myMapService;
    private final UserService userService;


    //회원 맛집 목록 삭제
    @GetMapping("/delete/{mapNo}")
    public String deleteHotplList(@PathVariable("mapNo") Integer mapNo,
                                  Principal principal) {
        User loginUser = userService.getUser(principal.getName());

        //1. 파라미터 받기 - mapNo
        // 서비스를 호출하여 해당 항목 삭제
        myMapService.deleteHotpl(mapNo);
       // return "redirect:/mymap/" + principal.getName(); // 삭제 후 리다이렉트
        return "redirect:/mymap/{user_id}"; // 삭제 후 리다이렉트
    }


    // 회원별 지도를 보여줄 페이지 및 JSON 데이터를 반환
    @GetMapping("/{user_id}")
    public ModelAndView showUserMyMap(@PathVariable("user_id") String user_id, Model model, Principal principal) {
        User loginUser = userService.getUser(principal.getName());
        int userNo = loginUser.getUser_no();

        if (!"admin".equals(principal.getName()) && !user_id.equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 사용자의 맛집지도에 접근할 권한이 없습니다");
        }

        List<MyMapDTO> hotplList = myMapService.getHotplacesByUserNo(userNo);

        // ModelAndView를 사용하여 View 이름과 데이터 모델을 설정
        ModelAndView modelAndView = new ModelAndView("myMap/kakaoMapT"); // View 이름 설정
        modelAndView.addObject("userNo", userNo); // 모델 데이터 추가
        modelAndView.addObject("user", loginUser);
        modelAndView.addObject("hotplList", hotplList);

        return modelAndView;
    }

    // JSON 데이터를 반환할 엔드포인트 =>
    @GetMapping("/hotplaces/{user_id}")
    @ResponseBody
    public List<MyMapDTO> myMapHotplList(@PathVariable("user_id") String user_id, Principal principal) {
        User loginUser = userService.getUser(principal.getName());
        int userNo = loginUser.getUser_no();

        if (!"admin".equals(principal.getName()) && !user_id.equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 사용자의 맛집지도에 접근할 권한이 없습니다");
        }
        List<MyMapDTO> myHotplList = myMapService.getHotplacesByUserNo(userNo);

        return myHotplList; // JSON 데이터 반환
    }


}