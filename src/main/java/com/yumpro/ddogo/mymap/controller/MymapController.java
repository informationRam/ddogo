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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // 회원별 지도를 보여줄 페이지 및 JSON 데이터를 반환
    @GetMapping("/{user_id}")
    public String showUserMyMap(@PathVariable("user_id") String user_id,
                                @RequestParam(value = "page", defaultValue = "0") int page,
                                @RequestParam(value = "pageSize", defaultValue = "4") int pageSize,
                                @RequestParam(value = "search", required = false) String search,
                                Model model, Principal principal) {
        User loginUser = userService.getUser(principal.getName());
        int userNo = loginUser.getUser_no();

        if (!"admin".equals(principal.getName()) && !user_id.equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 사용자의 맛집지도에 접근할 권한이 없습니다");
        }

        List<MyMapDTO> hotplList;
        int totalItems;

        if (search != null && !search.isEmpty()) {
            // 검색어가 있는 경우
            hotplList = myMapService.getHotplacesByUserNoWithSearch(userNo, search, pageSize, page * pageSize);
            totalItems = hotplList.size(); // 검색 결과의 총 아이템 수
        } else {
            // 검색어가 없는 경우
            hotplList = myMapService.getHotplacesByUserNo(userNo);
            totalItems = hotplList.size(); // 전체 아이템 수
        }

        // 페이지네이션 설정
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<MyMapDTO> hotplPage = new PageImpl<>(hotplList, pageable, totalItems);

        model.addAttribute("userNo", userNo);
        model.addAttribute("user", loginUser);
        model.addAttribute("hotplPage", hotplPage);
        model.addAttribute("hotplList", hotplPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", hotplPage.getTotalPages());

        return "myMap/kakaoMapT";
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