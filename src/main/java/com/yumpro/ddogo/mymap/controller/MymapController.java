package com.yumpro.ddogo.mymap.controller;

import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.emoAnal.service.EmoService;
import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import com.yumpro.ddogo.mymap.domain.ReviewDTO;
import com.yumpro.ddogo.mymap.domain.ReviewUpdateDTO;
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
    private final ReviewService reviewService;
    private final UserService userService;
    private final EmoService emoService;


    //mapNo로 모달에 기존 후기 보여주기
    @GetMapping("/getReview/{mapNo}")
    @ResponseBody
    public ResponseEntity<ReviewDTO> getUserReview(@PathVariable("mapNo") Integer mapNo) {

        //mapNo를 기반으로 데이터베이스에서 해당 후기를 조회하고 ReviewDTO로 반환
        ReviewDTO reviewDTO = reviewService.getReviewByMapNo(mapNo);
        System.out.println("mapNo:"+mapNo);
        if (reviewDTO != null) {
            return ResponseEntity.ok(reviewDTO);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
//
//    // 모달 후기수정
//    @PostMapping(value="/updateReview/{mapNo}",
//            consumes ="application/json",
//            produces={MediaType.TEXT_PLAIN_VALUE})
//    public ResponseEntity<String> updateReview(@PathVariable Integer mapNo,
//                                               @RequestBody ReviewUpdateDTO request
//                                               ) {
//        try {
//            // request 객체에 포함된 데이터 사용
//            String review = request.getReview();
//            char recom = request.getRecomm();
//            String memo = request.getMemo();
//            int hotplaceNo = request.getHotplaceNo();
//
//            // 클라이언트로부터 받은 후기 내용을 감정 분석 서비스로 분석
//            double emoResult = emoService.emoAnal(request.getReview());
//
//            // 감정 분석 결과 서비스 전달, 후기 저장 :request는 dto
//            reviewService.updateReviewandMemo(mapNo, request, emoResult);
//
//            return ResponseEntity.ok("업데이트가 성공적으로 수행되었습니다.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 중 오류가 발생했습니다.");
//        }
//    }


/*
    // 모달 후기수정
    @PostMapping(value="/updateReview/{mapNo}",
    consumes ="application/x-www-form-urlencoded",
    produces={MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> updateReviewAndMemo(@PathVariable Integer mapNo,
                                                      @RequestBody ReviewUpdateDTO request) {
        try {

            // mapNo를 DTO에 설정
            request.setMapNo(mapNo);
            // 여기서 emoReviewDTO 객체에 클라이언트로부터 받은 데이터가 매핑됩니다.
            // 클라이언트로부터 받은 후기 내용을 감정 분석 서비스로 분석
            double emoResult = emoService.emoAnal(request.getReview());

            // 감정 분석 결과를 DTO에 설정
            request.setEmo_result(emoResult);
            // 컨트롤러 메서드에서 memo와 recomm 저장
            request.setMemo(request.getMemo());
            request.setRecomm(request.getRecomm());

            // 서비스를 사용하여 후기정보 업데이트
            reviewService.updateReview(request);

            return ResponseEntity.ok("업데이트가 성공적으로 수행되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 중 오류가 발생했습니다.");
        }
    }*/
        // 모달 후기수정
        @PostMapping(value="/updateReview/{mapNo}",
                consumes ="application/json",
                produces={MediaType.TEXT_PLAIN_VALUE})
        public ResponseEntity<String> updateReviewAndMemo(@PathVariable Integer mapNo,
                                                          @RequestBody ReviewUpdateDTO request) {
            try {

                // mapNo를 DTO에 설정
                request.setMapNo(mapNo);
                // 여기서 emoReviewDTO 객체에 클라이언트로부터 받은 데이터가 매핑됩니다.
                // 클라이언트로부터 받은 후기 내용을 감정 분석 서비스로 분석
                double emoResult = emoService.emoAnal(request.getReview());

                // 감정 분석 결과를 DTO에 설정
                request.setEmo_result(emoResult);

                // Review 필드가 누락되었을 때 처리
                if (request.getReview() == null || request.getReview().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("후기는 필수 입력 항목입니다.");
                }

                // 컨트롤러 메서드에서 memo와 recomm 저장
                request.setMemo(request.getMemo());
                request.setRecomm(request.getRecomm());

                // 서비스를 사용하여 후기정보 업데이트
                reviewService.updateReview(request);

                return ResponseEntity.ok("후기가 성공적으로 수행되었습니다.");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("후기 업로드중 오류가 발생했습니다.");
            }
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


//    //ajax용 페이지네이션
//    @GetMapping("/api/{user_id}/{pageNo}")
//    @ResponseBody
//    public Page<MyMapDTO> myMapHotplListApi(
//            @PathVariable("user_id") String user_id,
//            @PathVariable("pageNo") int pageNo, // 파라미터 이름 수정
//            @RequestParam(defaultValue = "4") int size,
//            Principal principal) {
//        User loginUser = userService.getUser(principal.getName());
//        int userNo = loginUser.getUser_no();
//
//        // 페이지네이션을 위해 Pageable 객체 생성
//        Pageable pageable = PageRequest.of(pageNo - 1, size); // 페이지는 0부터 시작하므로 -1
//
//        return myMapService.getHotplacesByUserNo(userNo, pageable);
//    }



    // 회원별 맛집 목록 & 지도를 보여줄 페이지 및 JSON 데이터를 반환
    @GetMapping("/{user_id}")
    public ModelAndView showUserMyMap(
            @PathVariable("user_id") String user_id,
            @RequestParam(defaultValue="1") int page, // 기본 페이지 1
            @RequestParam(defaultValue="4") int size, // 페이지당 카드 수 4
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