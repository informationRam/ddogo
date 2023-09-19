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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

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

  /*  //검색창 결과
    @GetMapping("/{user_id}/search")
    @ResponseBody
    public List<MyMapDTO> searchHotplaces(@PathVariable("user_id") String user_id, @RequestParam("query") String query, Principal principal) {
        User loginUser = userService.getUser(principal.getName());
        int userNo = loginUser.getUser_no();

        if (!"admin".equals(principal.getName()) && !user_id.equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 사용자의 맛집지도에 접근할 권한이 없습니다");
        }

        // 서비스를 통해 검색 로직 수행
        List<MyMapDTO> searchResult = myMapService.searchHotplaces(userNo, query);

        return searchResult; // JSON 데이터 반환
    }*/

        // 회원별 지도를 보여줄 페이지 반환
        @GetMapping("/{user_id}")
        public String showUserMyMap(@PathVariable("user_id") String user_id,
                                    @RequestParam(name = "userNo") Integer userNo,
                                    @RequestParam(value = "search", required = false) String search,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "pageSize", defaultValue = "4") int pageSize,
                                    Model model, Principal principal) {
            
            //userNo 정보 받기
            User loginUser = userService.getUser(principal.getName());
            Integer userNo = loginUser.getUser_no();
            System.out.println("user넘버="+userNo);

            if (!"admin".equals(principal.getName()) && !user_id.equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 사용자의 맛집지도에 접근할 권한이 없습니다");
            }

            // 페이지 번호와 페이지 크기를 기반으로 offset을 계산
            int offset = (page - 1) * pageSize;

            // 검색어가 있는 경우와 없는 경우에 따라 다른 쿼리를 실행
            List<MyMapDTO> hotplList;
            int totalItemCount;

            if (search != null && !search.isEmpty()) {
                // 검색어가 있는 경우
                hotplList = myMapService.getHotplacesByUserNo(userNo, search, pageSize, offset);
                totalItemCount = myMapService.countHotplacesByUserNoWithSearch(userNo, search); // 검색 결과의 총 아이템 수 계산
            } else {
                // 검색어가 없는 경우
                hotplList = myMapService.getHotplacesByUserNo(userNo, null, pageSize, offset);
                totalItemCount = myMapService.countHotplacesByUserNo(userNo); // 전체 아이템 수 계산
            }

            // 총 페이지 수 계산
            int totalPages = (int) Math.ceil((double) totalItemCount / pageSize);

            model.addAttribute("userNo", userNo);
            model.addAttribute("user", loginUser);
            model.addAttribute("hotplList", hotplList);
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", totalPages);

            return "myMap/kakaoMapT";
        }


    // JSON 데이터를 반환할 엔드포인트 =>
    @GetMapping("/hotplaces/{user_id}")
    @ResponseBody
    public ResponseEntity<List<MyMapDTO>> myMapHotplList(
            @PathVariable("user_id") String user_id,
            @RequestParam(name = "userNo") Integer userNo,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "4") int pageSize,
            Principal principal) {

        User loginUser = userService.getUser(principal.getName());
        int userNo = loginUser.getUser_no();

        if (!"admin".equals(principal.getName()) && !user_id.equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 사용자의 맛집지도에 접근할 권한이 없습니다");
        }

        // 페이지 번호와 페이지 크기를 기반으로 offset을 계산
        int offset = (page - 1) * pageSize;

        List<MyMapDTO> hotplList;
        int totalItemCount;

        // 검색어가 있는 경우와 없는 경우에 따라 다른 쿼리를 실행
        if (search != null && !search.isEmpty()) {
            // 검색어가 있는 경우
            hotplList = myMapService.getHotplacesByUserNo(userNo, search, pageSize, offset);
            totalItemCount = myMapService.countHotplacesByUserNoWithSearch(userNo, search); // 검색 결과의 총 아이템 수 계산
        } else {
            // 검색어가 없는 경우
            hotplList = myMapService.getHotplacesByUserNo(userNo, pageSize, offset);
            totalItemCount = myMapService.countHotplacesByUserNo(userNo); // 전체 아이템 수 계산
        }

        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalItemCount / pageSize);

        // 페이지 정보를 헤더에 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Size", String.valueOf(pageSize));
        headers.add("X-Total-Items", String.valueOf(totalItemCount));
        headers.add("X-Total-Pages", String.valueOf(totalPages));

        return ResponseEntity.ok()
                .headers(headers)
                .body(hotplList); // JSON 데이터 반환
    }



}