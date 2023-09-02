package com.yumpro.ddogo.searchmap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumpro.ddogo.searchmap.dto.SearchMapDTO;
import com.yumpro.ddogo.searchmap.dto.TestDTO;
import com.yumpro.ddogo.searchmap.service.SearchMapService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.security.Principal;

@RequestMapping("/search")
@RequiredArgsConstructor
@Controller
public class SearchMapController {
    //private final SearchMapService searchMapService;
    //private final SearchMapDTO searchMapDTO;

    //검색할 수 있게 지도 띄워줘 요청
    /*요청주소: http://localhost/search
    * 요청방식: get*/
    @GetMapping("")
    public String showMap(){
        return "searchmap/searchMap";
    }

    //마커를 클릭했을 때, 해당 장소를 내 지도에 저장해줘 요청
    /*요청주소: http://localhost/search/add
    * 요청방식: post*/
    /*form 의 input type="hidden" 으로 값 가져오는 메소드: 됨*/
    @PostMapping("/add")
    public String testAction2(@RequestParam double markerLat, @RequestParam double markerLng,
                              @RequestParam String placeName, @RequestParam String placeAddress,
                              @RequestParam String placeCateCode, @RequestParam String myRecommend,
                              @RequestParam String inputReview, @RequestParam String inputMemo,
                              Principal principal
                              ){ //Principal도 추가해야함. 로그인한 유저 정보 가져와야하니까.

        //1. 파라미터받아서 DTO에 담기
        //로그인한 유저 정보 가져오기
        System.out.println("principal.getName():"+principal.getName());
        //도로명 주소 자르기 예:제주특별자치도->제주 서귀포시
        String afterSido = placeAddress.substring(placeAddress.indexOf(" ")+1); //첫번째 공백 이후 부터 끝까지.
        String sido = placeAddress.substring(0, 2);
        String gugun = afterSido.substring(0, afterSido.indexOf(" "));
        /*//DTO에 담기
        searchMapDTO.setLng(markerLng); //경도
        searchMapDTO.setLat(markerLat); //위도
        searchMapDTO.setPlaceName(placeName); //상호명
        searchMapDTO.setAddress(placeAddress);//전체도로명주소
        searchMapDTO.setSido(sido); //시, 도
        searchMapDTO.setGugun(gugun); //시,군,구
        searchMapDTO.setInputReview(inputReview);
        searchMapDTO.setInputMemo(inputMemo);
        searchMapDTO.setMyRecommend(myRecommend);
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
        //  리턴: 방금 mymap에 입력된 pk인 map_no의 값
        int mapNo = searchMapService.insertMyMap(searchMapDTO);*/

        // 3) 입력받은 review감정분석
        //  리턴: 감정분석결과
        //double emo_result = emoService.emoAnal(searchMapDTO.getInputReview());

        // 4) 분설결과와 함께 emoreview 테이블에 insert
        //searchMapService.insertEmoReview(emo_result, searchMapDTO);

        //3. 모델 4. 뷰
        //성공했으면, 잘 저장되었다고 alret하나 띄워줬으면..

        return String.format("redirect:/search");
    }

}
