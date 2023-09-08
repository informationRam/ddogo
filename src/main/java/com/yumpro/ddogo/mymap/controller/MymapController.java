package com.yumpro.ddogo.mymap.controller;

import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.mymap.domain.EmoReviewDTO;
import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import com.yumpro.ddogo.mymap.service.MymapService;
import com.yumpro.ddogo.mymap.service.ReviewService;
import com.yumpro.ddogo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/mymap")
public class MymapController {

    private final MymapService myMapService;
    private final UserService userService;
    private final ReviewService reviewService;



    //mapNo로 모달에 기존 후기 보여주기
    @GetMapping("/getReview/{mapNo}")
    @ResponseBody
    public ResponseEntity<EmoReviewDTO> getUserReview(@PathVariable("mapNo") Integer mapNo) {

        // reviewId를 기반으로 데이터베이스에서 해당 후기를 조회하고 ReviewDTO로 반환
        EmoReviewDTO emoReviewDTO = reviewService.getReviewByMapNo(mapNo);
        System.out.println("mapNo:"+mapNo);
        if (emoReviewDTO != null) {
            return ResponseEntity.ok(emoReviewDTO);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // 모달 후기수정
    @PostMapping(value="/updateReview/{mapNo}",
    consumes ="application/json",
    produces={MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> updateReviewAndMemo(@PathVariable Integer mapNo,
                                                      @RequestBody EmoReviewDTO emoReviewDTO) {
        try {
            // 여기서 emoReviewDTO 객체에 클라이언트로부터 받은 데이터가 매핑됩니다.
            // mapNo는 PathVariable로 받으므로 따로 설정하지 않아도 됩니다.
            reviewService.updateReview(emoReviewDTO);
            return ResponseEntity.ok("업데이트가 성공적으로 수행되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 중 오류가 발생했습니다.");
        }
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        e.printStackTrace(); // 예외 스택 트레이스를 출력
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 중 오류가 발생했습니다.");
    }

    //회원 맛집 목록 삭제
    @GetMapping("/delete/{mapNo}")
    public String deleteHotplList(@PathVariable("mapNo") Integer mapNo,
                                  Principal principal) {
        User loginUser = userService.getUser(principal.getName());

        //1. 파라미터 받기 - mapNo
        // 서비스를 호출하여 해당 항목 삭제
        myMapService.deleteHotpl(mapNo);
        return "redirect:/mymap/" + principal.getName(); // 삭제 후 리다이렉트
    }


    // 회원별 맛집 목록 & 지도를 보여줄 페이지 및 JSON 데이터를 반환
    @GetMapping("/{user_id}")
    public ModelAndView showUserMyMap(
            @PathVariable("user_id") String user_id,
            @RequestParam(defaultValue="1") int page, // 기본 페이지 1
            @RequestParam(defaultValue="3") int size, // 페이지당 카드 수 4
            Model model, Principal principal) {

        User loginUser = userService.getUser(principal.getName());
        int userNo = loginUser.getUser_no();

        if (!"admin".equals(principal.getName()) && !user_id.equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 사용자의 맛집지도에 접근할 권한이 없습니다");
        }

        // 페이지네이션을 위해 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page - 1, size); // 페이지는 0부터 시작하므로 -1

        Page<MyMapDTO> hotplPage = myMapService.getHotplacesByUserNo(userNo, pageable);

        // ModelAndView를 사용하여 View 이름과 데이터 모델을 설정
        ModelAndView modelAndView = new ModelAndView("myMap/kakaoMapT"); // View 이름 설정
        modelAndView.addObject("userNo", userNo); // 회원번호
        modelAndView.addObject("user", loginUser); // 로그인 회원 정보 all
        modelAndView.addObject("hotplList", hotplPage.getContent()); // 현재 페이지의 데이터만 추가
        modelAndView.addObject("totalPages", hotplPage.getTotalPages()); // 전체 페이지 수 추가
        modelAndView.addObject("currentPage", page); // 현재 페이지 번호 추가

        return modelAndView;
    }

    // JSON 데이터를 반환할 엔드포인트 =>
    @GetMapping("/hotplaces/{user_id}")
    @ResponseBody
    public Page<MyMapDTO> myMapHotplList(
            @PathVariable("user_id") String user_id,
            @RequestParam(defaultValue="1") int page,// 기본 페이지 1
            @RequestParam(defaultValue="4") int size, // 페이지당 카드 수 4
            Principal principal) {

        User loginUser = userService.getUser(principal.getName());
        int userNo = loginUser.getUser_no();

        if (!"admin".equals(principal.getName()) && !user_id.equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 사용자의 맛집지도에 접근할 권한이 없습니다");
        }
        // 페이지네이션을 위해 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page - 1, size); // 페이지는 0부터 시작하므로 -1
        Page<MyMapDTO> myHotplPage  = myMapService.getHotplacesByUserNo(userNo, pageable);

        return myHotplPage ; // JSON 데이터 반환
    }


}