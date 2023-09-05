package com.yumpro.ddogo.searchmap.controller;

import com.yumpro.ddogo.common.entity.Hotplace;
import com.yumpro.ddogo.common.entity.MyMap;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.searchmap.dto.SearchMapDTO;
import com.yumpro.ddogo.searchmap.repository.HotplaceRepository;
import com.yumpro.ddogo.searchmap.repository.MymapRepository;
import com.yumpro.ddogo.searchmap.service.SearchMapService;
import com.yumpro.ddogo.user.service.UserService;
import com.yumpro.ddogo.emoAnal.service.EmoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;

@RequestMapping("/search")
@RequiredArgsConstructor
@Controller
public class SearchMapController {
    private final SearchMapService searchMapService;
    private final UserService userService;
    private final EmoService emoService;

    private final HotplaceRepository hotplaceRepository; //지울거임
    private final MymapRepository mymapRepository; //지울거임

    //검색할 수 있게 지도 띄워줘 요청
    /*요청주소: http://localhost/search
    * 요청방식: get*/
    @GetMapping("")
    public String showMap(){
        return "searchmap/searchMap";
    }

    //(유효성검사 자바스크립트로)마커를 클릭했을 때, 해당 장소를 내 지도에 저장해줘 요청
    /*요청주소: http://localhost/search/add
    * 요청방식: post*/
    @PostMapping("/add")
    //@PreAuthorize("isAuthenticated()") //로그인한 사람만 저장 가능
    public String add(@RequestParam double markerLat, @RequestParam double markerLng,
                      @RequestParam String placeName, @RequestParam String placeAddress,
                      @RequestParam String placeCateCode, @RequestParam char myRecommend,
                      @RequestParam String inputReview, @RequestParam String inputMemo,
                      Principal principal ){ //로그인한 유저 정보 가져오기
        //데이터 담을 DTO 생성
        SearchMapDTO searchMapDTO = new SearchMapDTO();

        //1. 파라미터받기
        // 1) 먼저 입력한 적 있는지 확인. 이미 저장된 맛집입니다.
        //  (1) 위도, 경도로 hotplace 검색. DB에 입력된 적 있는지 확인. 중복입력방지
        //파라미터로 넘어온 위도 경도로 select.

        //  (2) 회원번호: mymap 검색할 때 필요.
        //로그인한 유저 정보 가져오기

        User user = userService.getUser(principal.getName());
        SearchMapDTO hotplaceResult = searchMapService.findByLatAndLng(markerLat, markerLng);
        //Hotplace hotplaceResult = searchMapService.findByLatAndLng(markerLat, markerLng);
        SearchMapDTO mymapResult = searchMapService.findHistory(user, hotplaceResult);
        if(hotplaceResult!=null && mymapResult!=null){ //mymap에 저장된 적 있으면
            System.out.println("내 지도에 저장된 적 있음");
            return "redirect:/search";
        } else if (hotplaceResult!=null) { //나는 저장하지 않았지만 다른 사용자가 저장한 적 있으면
            System.out.println("내 지도엔 없지만 디비엔 있음");
            searchMapDTO.setHotplaceNo(hotplaceResult.getHotplaceNo());
            searchMapDTO.setLat(hotplaceResult.getLat());
            searchMapDTO.setLng(hotplaceResult.getLng());
            searchMapDTO.setPlaceName(hotplaceResult.getPlaceName());
            searchMapDTO.setAddress(hotplaceResult.getAddress());
            searchMapDTO.setSido(hotplaceResult.getSido());
            searchMapDTO.setGugun(hotplaceResult.getGugun());
        } else { //새로운 곳을 내가 맨 처음 저장하는 경우
            //도로명 주소 자르기 예:제주특별자치도->제주 서귀포시
            String afterSido = placeAddress.substring(placeAddress.indexOf(" ")+1); //첫번째 공백 이후 부터 끝까지.
            String sido = placeAddress.substring(0, 2);
            String gugun = afterSido.substring(0, afterSido.indexOf(" "));

            //DTO에 담기
            //한번도 등록된 적 없는 새로운 장소인 경우
            searchMapDTO.setLng(markerLng); //경도
            searchMapDTO.setLat(markerLat); //위도
            // DTO placeCateNo필드에 코드에 따라 no부여
            if(placeCateCode.equals("FD6")){
                searchMapDTO.setPlaceCateNo(1);
            } else {
                searchMapDTO.setPlaceCateNo(2);
            }
            searchMapDTO.setPlaceName(placeName); //상호명
            searchMapDTO.setAddress(placeAddress);//전체도로명주소
            searchMapDTO.setSido(sido); //시, 도
            searchMapDTO.setGugun(gugun); //시,군,구

            searchMapDTO.setInputReview(inputReview);
            searchMapDTO.setInputMemo(inputMemo);
            searchMapDTO.setMyRecommend(myRecommend);
        }

        /*//2. 비즈니스로직처리
        // 1) hotplace 테이블에 insert
        //  리턴: 방금 hotplace에 입력된 pk인 hotplace_no의 값
        int hotplaceNo = searchMapService.insertHotpalce(searchMapDTO);

        // 2) mymap 테이블에 insert
        // 파라미터: 로그인한 유저번호, 방금 hotplace에 입력된 pk인 hotplace_no의 값
        //  리턴: 방금 mymap에 입력된 pk인 map_no의 값
        int mapNo = searchMapService.insertMyMap(user, hotplaceNo, searchMapDTO);

        // 3) 입력받은 review감정분석
        // 파라미터: form에 입력받은 review
        //  리턴: 감정분석결과
        double emoResult = emoService.emoAnal(searchMapDTO.getInputReview());

        // 4) 분설결과와 함께 emoreview 테이블에 insert
        // 파라미터: 분석결과, 방금 입력한 hotplace_no, 방금 입력한 map_no
        searchMapService.insertEmoReview(emoResult, hotplaceNo, mapNo, searchMapDTO);

        //3. 모델 4. 뷰
        //성공했으면, 잘 저장되었다고 alret하나 띄워줬으면..*/

        return "redirect:/search";
    }

    /*(모달폼, 바인딩리절트, 타임리프)마커를 클릭했을 때, 해당 장소를 내 지도에 저장해줘 요청
    /*요청주소: http://localhost/search/add
     * 요청방식: post
    @PostMapping("/add")
    //@PreAuthorize("isAuthenticated()") //로그인한 사람만 저장 가능
    public String add(@Valid ModalForm modalForm, //유효성검사를 거친 녀석들
                      BindingResult bindingResult, //유효성검사 결과
                      @RequestParam double markerLat, @RequestParam double markerLng,
                      @RequestParam String placeName, @RequestParam String placeAddress,
                      @RequestParam String placeCateCode,
                      Principal principal ){ //로그인한 유저 정보 가져오기
        System.out.println("add()진입");
        if(!bindingResult.hasErrors()){ //폼 형식에 에러가 있으면,
            System.out.println("폼형식에 에러있음");
            //alert하나 띄워줬으면..
            return "redirect:/search"; //검색화면으로 돌아감
        }
        //먼저 입력한 적 있는지 확인. 이미 저장된 맛집입니다.

        SearchMapDTO searchMapDTO = new SearchMapDTO();
        //1. 파라미터받아서 DTO에 담기
        //로그인한 유저 정보 가져오기
        User user = userService.getUser(principal.getName());

        //도로명 주소 자르기 예:제주특별자치도->제주 서귀포시
        String afterSido = placeAddress.substring(placeAddress.indexOf(" ")+1); //첫번째 공백 이후 부터 끝까지.
        String sido = placeAddress.substring(0, 2);
        String gugun = afterSido.substring(0, afterSido.indexOf(" "));

        //DTO에 담기
        searchMapDTO.setLng(markerLng); //경도
        searchMapDTO.setLat(markerLat); //위도
        searchMapDTO.setPlaceName(placeName); //상호명
        searchMapDTO.setAddress(placeAddress);//전체도로명주소
        searchMapDTO.setSido(sido); //시, 도
        searchMapDTO.setGugun(gugun); //시,군,구
        searchMapDTO.setInputReview(modalForm.getInputReview()); //유효성검사를 거친 리뷰
        searchMapDTO.setInputMemo(modalForm.getInputMemo()); //유효성검사를 거친 메모
        searchMapDTO.setMyRecommend(modalForm.getMyRecommend().name()); //유효성검사를 거친 추천여부
        // DTO placeCateNo필드에 코드에 따라 no부여
        if(placeCateCode.equals("FD6")){
            searchMapDTO.setPlaceCateNo(1);
        } else {
            searchMapDTO.setPlaceCateNo(2);
        }

        //2. 비즈니스로직처리
        // 1) hotplace 테이블에 insert
        //  리턴: 방금 hotplace에 입력된 pk인 hotplace_no의 값
        int hotplaceNo = searchMapService.insertHotpalce(searchMapDTO);

        // 2) mymap 테이블에 insert
        // 파라미터: 로그인한 유저번호, 방금 hotplace에 입력된 pk인 hotplace_no의 값
        //  리턴: 방금 mymap에 입력된 pk인 map_no의 값
        int mapNo = searchMapService.insertMyMap(user, hotplaceNo, searchMapDTO);

        // 3) 입력받은 review감정분석
        // 파라미터: form에 입력받은 review
        //  리턴: 감정분석결과
        double emoResult = emoService.emoAnal(searchMapDTO.getInputReview());

        // 4) 분설결과와 함께 emoreview 테이블에 insert
        // 파라미터: 분석결과, 방금 입력한 hotplace_no, 방금 입력한 map_no
        searchMapService.insertEmoReview(emoResult, hotplaceNo, mapNo, searchMapDTO);

        //3. 모델 4. 뷰
        //성공했으면, 잘 저장되었다고 alret하나 띄워줬으면..

        return "redirect:/search";
    }*/

}
