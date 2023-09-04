    package com.yumpro.ddogo.mymap.controller;

    import com.yumpro.ddogo.mymap.domain.MyMapDTO;
    import com.yumpro.ddogo.mymap.service.MymapService;
    import com.yumpro.ddogo.user.service.UserService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestMapping;

    import java.util.List;


    @RequiredArgsConstructor
  /*  @RestController*/
    @Controller
    @RequestMapping("/mymap")
    public class MymapController {

        private final MymapService myMapService;
        private final UserService userService;

        //http://localhost/mymap/user/100
        //회원별 맛집 리스트 보여주기
        @GetMapping("/userMap/{userNo}")
    //1번 주석해제 : 회원 인증 단계 거치기
    //Mapper와 service 에서 findbyUserno 주석처리하고 Userservice에서 userno받아오는거로 다시 변경필요

    /*    public ResponseEntity<?> showUserMyMap(@PathVariable("userNo") Integer userNo, Model model,
                                                            Principal principal) { */

        public String showUserMyMap(@PathVariable("userNo") Integer userNo, Model model) {

    /* 2번주석해제
            // 사용자 정보 가져오기
            User loginUser = userService.getUser(principal.getName());


            if (loginUser == null) {
                // 사용자를 찾지 못한 경우
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("사용자를 찾을 수 없습니다.");
            }

            if (loginUser.getUser_no() != userNo) {
                // 사용자 번호가 일치하지 않는 경우
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("해당 사용자의 맛집 리스트에 접근할 권한이 없습니다.");
            }
    */
            // 회원이 저장한 맛집 리스트 가져오기
            List<MyMapDTO> myHotplList = myMapService.getHotplacesByUserNo(userNo);

            // 가져온 정보 모델 저장-> Thymeleaf 통해 뷰로 전달
    //3번 주석해제
            /*model.addAttribute("user", loginUser);*/
            model.addAttribute("myHotplList", myHotplList);

            // 회원 번호(userNo)를 사용하여 해당 회원이 저장한 맛집 리스트를 조회하고 MyMapDTO 목록을 반환합니다.


    //4번 주석해제=>


            //return myHotplList;  //json 전달 확인
           return  "myMap/kakaoMap2" ; //html로 바로 링크
        }
    }



