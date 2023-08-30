package com.yumpro.ddogo.searchmap.controller;

import com.yumpro.ddogo.searchmap.dto.SearchMapDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SearchMapController {
    @GetMapping("/search")
    public String showMap(){
        return "searchmap/testSearchMap";
    }

    @GetMapping("/")
    public String test(){
        return "searchmap/customOverlay";
    }
//    @RequestMapping(value = "/search/add", method = RequestMethod.POST)
//    @ResponseBody
//    public String addMyMap(
//            @RequestBody String filterJSON,
//            HttpServletResponse response,
//            ModelMap model ) throws Exception {
//
//        JSONObject resMap = new JSONObject();
//
//        try{
//            ObjectMapper mapper = new ObjectMapper();
//            SearchMapDTO searchMapDTO = (SearchMapDTO)mapper.readValue(filterJSON,new TypeReference<SearchMapDTO>(){ });
//
//            SearchMapService.addMyMap(SearchMapDTO);
//
//            resMap.put("res", "success");
//            resMap.put("msg", "저장하였습니다.");
//
//        }catch(Exception e){
//
//        }
//        response.setContentType("text/html; charset=UTF-8");
//        PrintWriter out = response.getWriter();
//        out.print(resMap);
//
//        return null;
//    }
}
