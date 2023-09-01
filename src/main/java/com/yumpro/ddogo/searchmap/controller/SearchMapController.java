package com.yumpro.ddogo.searchmap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumpro.ddogo.searchmap.dto.SearchMapDTO;
import com.yumpro.ddogo.searchmap.dto.TestDTO;
import com.yumpro.ddogo.searchmap.service.SearchMapService;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;

@Controller
public class SearchMapController {
    SearchMapService searchMapService;

    //검색할 수 있게 지도 띄워줘 요청
    /*요청주소: http://localhost/search
    * 요청방식: get*/
    @GetMapping("/search")
    public String showMap(){
        return "searchmap/testSearchMap2";
    }

    //커스텀오버레이를 확인하는 메소드
    @GetMapping("/")
    public String test(){
        return "searchmap/customOverlay";
    }

    //마커를 클릭했을 때, 해당 장소를 내 지도에 저장해줘 요청
    /*요청주소: http://localhost/search/add
    * 요청방식: post*/
    /*form 의 input type="hidden" 으로 값 가져오는 메소드: 됨*/
    @PostMapping("/search/add")
    public String testAction2(@RequestParam double markerLat,
                              @RequestParam double markerLng,
                              @RequestParam String placesName,
                              @RequestParam String placesAddress,
                              @RequestParam String inputReview,
                              @RequestParam String inputMemo,
                              @RequestParam String myRecommend
                              ){
        //1. 파라미터받기
        System.out.println("markerLat: "+markerLat);
        System.out.println("markerLng: "+markerLng);
        System.out.println("placesName: "+placesName);
        System.out.println("placesAddress: "+placesAddress);
        System.out.println("inputReview: "+inputReview);
        System.out.println("inputMemo: "+inputMemo);
        System.out.println("myRecommend: "+myRecommend);

        //2. 비즈니스로직처리
        searchMapService.addMyMap();

        //3. 모델 4. 뷰


        return String.format("redirect:/search");
    }

    /*@PostMapping("/search/add")
    @ResponseBody
    public String testAction1(@RequestBody String filterJSON,
                           HttpServletResponse response,
                           ModelMap model) throws Exception{
        System.out.println("testAction1()진입");
        JSONObject resMap = new JSONObject();
        System.out.println("JSONObject 객체 생성 완료");
        try{
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("ObjectMapper 객체 생성 완료");
            SearchMapDTO searchMapDTO = (SearchMapDTO)mapper.readValue(filterJSON, new TypeReference<SearchMapDTO>() {
            });
            System.out.println("searchMapDTO에 담기 성공");
            //searchMapService.insertDTO(searchMapDTO);
            resMap.put("msg", "완료");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(resMap);
        return null;
    }*/

    /*@PostMapping("/search/add")
    @ResponseBody // JSON 응답을 생성할 때 사용
    public ResponseEntity<String> addData(@RequestBody String jsonData) {
        System.out.println("add()진입");
        try {
            // JSON 데이터를 YourData 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println("objectMapper 생성 완료");
            TestDTO data = objectMapper.readValue(jsonData, TestDTO.class);
            System.out.println("objectMapper.readValue 성공");
            System.out.println("상호명: "+data.getPlacesName());
            System.out.println("경도: "+data.getMarkerLng());
            System.out.println("메모: "+data.getInputMemo());
            // data 객체를 통해 JSON 데이터를 사용하여 원하는 작업을 수행합니다.

            // 예를 들어, 데이터를 로깅하거나 데이터베이스에 저장하는 등의 작업을 수행할 수 있습니다.

            // 성공 응답 반환
            return ResponseEntity.ok("Data received successfully!");
        } catch (Exception e) {
            // 오류 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }*/
    
    /*@PostMapping("/search/add")
    public ResponseEntity<String> addData(@RequestBody TestDTO testDTO) {
        try {
            // data 객체를 통해 JSON 데이터를 사용하여 원하는 작업을 수행합니다.
            System.out.println("memo: "+testDTO.getInputMemo());
            System.out.println("추천여부: "+testDTO.getMyRecommend());
            System.out.println("식당이름: "+testDTO.getPlacesName());

            // 예를 들어, 데이터를 로깅하거나 데이터베이스에 저장하는 등의 작업을 수행할 수 있습니다.

            // 성공 응답 반환
            return ResponseEntity.ok("Data received successfully!");
        } catch (Exception e) {
            // 오류 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }*/
}
