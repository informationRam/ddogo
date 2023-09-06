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
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/search")
@RequiredArgsConstructor
@Controller
public class SearchMapController {
    private final SearchMapService searchMapService;
    private final UserService userService;
    private final EmoService emoService;
    private final HotplaceRepository hotplaceRepository;
    private final MymapRepository mymapRepository;
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
    public String add(@RequestParam double markerLat, @RequestParam double markerLng,
                              @RequestParam String placeName, @RequestParam String placeAddress,
                              @RequestParam String placeCateCode, @RequestParam char myRecommend,
                              @RequestParam String inputReview, @RequestParam String inputMemo,
                              Principal principal ){ //로그인한 유저 정보 가져오기
        Hotplace hotplace = hotplaceRepository.findByLatAndLng(markerLat, markerLng);
        if(hotplace.getHotplaceNo()==null){
            return "redirect:/search";
        }
        System.out.println("핫플no:"+hotplace.getHotplaceNo());
        //1. 파라미터받아서 DTO에 담기
        //로그인한 유저 정보 가져오기
        User user = userService.getUser(principal.getName());
        SearchMapDTO searchMapDTO = new SearchMapDTO();
        //JPA안됨
        // MyMap myMap = mymapRepository.findByUserNoAndHotplaceNo(user.getUser_no(), hotplace.getHotplaceNo());

        //MyBatis시도
        Map<String, Object> map = new HashMap<>();
        map.put("user_no", user.getUser_no());
        System.out.println("map user_no:"+map.get("user_no"));//됨
        map.put("hotplace_no", hotplace.getHotplaceNo());
        System.out.println("map user_no:"+map.get("hotplace_no"));//됨

        searchMapService.findHistory(map);
        //System.out.println("이미 저장됨. mymapNo:"+myMap.getMapNo());
        //도로명 주소 자르기 예:제주특별자치도->제주 서귀포시
        /*String afterSido = placeAddress.substring(placeAddress.indexOf(" ")+1); //첫번째 공백 이후 부터 끝까지.
        String sido = placeAddress.substring(0, 2);
        String gugun = afterSido.substring(0, afterSido.indexOf(" "));
        //DTO에 담기
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

        return String.format("redirect:/search");
    }

}
