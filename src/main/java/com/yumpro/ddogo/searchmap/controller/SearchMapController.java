package com.yumpro.ddogo.searchmap.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yumpro.ddogo.searchmap.dto.SearchMapDTO;
import com.yumpro.ddogo.searchmap.service.SearchMapService;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
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
        return "searchmap/testSearchMap";
    }

    //커스텀오버레이를 확인하는 메소드
    @GetMapping("/")
    public String test(){
        return "searchmap/customOverlay";
    }

    //마커를 클릭했을 때, 해당 장소를 내 지도에 저장해줘 요청
    /*요청주소: http://localhost/search/add
    * 요청방식: post*/
    @PostMapping("/search/add")
    @ResponseBody
    public String testAction(@RequestBody String filterJSON,
                           HttpServletResponse response,
                           ModelMap model) throws Exception{
        JSONObject resMap = new JSONObject();
        try{
            ObjectMapper mapper = new ObjectMapper();
            SearchMapDTO searchMapDTO = (SearchMapDTO)mapper.readValue(filterJSON, new TypeReference<SearchMapDTO>() {
            });
            System.out.println("searchMapDTO에 담기 성공");
            searchMapService.insertDTO(searchMapDTO);
            resMap.put("msg", "완료");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(resMap);
        return null;
    }
}
