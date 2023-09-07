package com.yumpro.ddogo.searchmap.controller;

import com.yumpro.ddogo.common.entity.Hotplace;
import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.searchmap.dto.BeforeLocation;
import com.yumpro.ddogo.searchmap.dto.SearchMapDTO;
import com.yumpro.ddogo.searchmap.exception.HotplaceNotFoundException;
import com.yumpro.ddogo.searchmap.repository.HotplaceRepository;
import com.yumpro.ddogo.searchmap.repository.MymapRepository;
import com.yumpro.ddogo.searchmap.service.SearchMapService;
import com.yumpro.ddogo.user.service.UserService;
import com.yumpro.ddogo.emoAnal.service.EmoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @RequestMapping("/ddo")
    public String showBeforeMap(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("beforeLocation");
        return "searchmap/searchMap2";
    }
    //(유효성검사 자바스크립트로)마커를 클릭했을 때, 해당 장소를 내 지도에 저장해줘 요청
    /*요청주소: http://localhost/search/add
    * 요청방식: post*/
    @PostMapping("/add")
    //@PreAuthorize("isAuthenticated()") //로그인한 사람만 저장 가능
    public String add(@RequestParam double markerLat, @RequestParam double markerLng, @RequestParam String placeName,
                      @RequestParam String placeRoadAddress, @RequestParam String placeJibunAddress, @RequestParam String placeCateCode,
                      @RequestParam char myRecommend, @RequestParam String inputReview, @RequestParam String inputMemo,
                      Principal principal, Model model ) throws Exception {

        //1. 파라미터받기
        //로그인한 유저 정보 가져오기
        User user = userService.getUser(principal.getName());

        if(!placeCateCode.equals("FD6") && !placeCateCode.equals("CE7")) { //음식점도 아니고 카페도 아닌 경우
            return "redirect:/search/cantSave";
        }
        //DB에 저장된 데이터가 있는지 확인
        Hotplace hotplace = hotplaceRepository.findByLatAndLng(markerLat, markerLng);

        if(hotplace != null && checkRecord(hotplace, user)!=null){
            System.out.println("내 지도에 이미 입력된 경우.");
        }
        if(hotplace != null && checkRecord(hotplace, user) == null){
            System.out.println("DB엔 있지만 내 지도엔 없는 경우. hotplace_no:"+hotplace.getHotplaceNo());
            // 1) DB에는 있지만 내지도에 저장된 적 없는 경우
            int mapNo = addMymap(hotplace, user, inputMemo, myRecommend); //방금입력된 mymap의 pk
            double emoResult = emoService.emoAnal(inputReview); //감정분석결과
            searchMapService.insertEmoReview(mapNo, hotplace.getHotplaceNo(), inputReview, emoResult);
        }
        if(hotplace == null){
            System.out.println("DB에 입력된 적 없는 경우. 새로 등록 진행");
            String placeAddress = "";
            if(placeRoadAddress=="" || placeRoadAddress==null){ //도로명주소가 없는 경우
                placeAddress = placeJibunAddress;
            } else {
                placeAddress = placeRoadAddress;
            }
            int hotplaceNo = addHotplace(markerLat, markerLng, placeName, placeAddress, placeCateCode);
            int mapNo = addMymap2(hotplaceNo, user, inputMemo, myRecommend);
            double emoResult = emoService.emoAnal(inputReview);
            searchMapService.insertEmoReview(mapNo, hotplaceNo, inputReview, emoResult);
        }

        //3. 모델 4. 뷰
        //성공했으면, 잘 저장되었다고 alret하나 띄워줬으면..*/
        BeforeLocation beforeLocation = new BeforeLocation(markerLat, markerLng);

        model.addAttribute("beforeLocation",beforeLocation);

        return "redirect:/search/ddo";
    }
    //DB에도 없고 내지도에도 없는 장소를 저장 -2(내지도에)
    private int addMymap2(int hotplaceNo, User user, String inputMemo, char myRecommend) {
        SearchMapDTO searchMapDTO = new SearchMapDTO();
        searchMapDTO.setMyRecommend(myRecommend);
        searchMapDTO.setInputMemo(inputMemo);
        return searchMapService.insertMyMap(user, hotplaceNo, searchMapDTO);
    }

    // 1) hotplace 테이블에 insert DB에도 없고 내지도에도 없는 장소를 저장 -1(핫플에)
    //리턴: 방금입력된 hotplace_no
    private int addHotplace(double markerLat, double markerLng,
                            String placeName, String placeAddress, String placeCateCode) {
        SearchMapDTO searchMapDTO = new SearchMapDTO();
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

        // DTO placeCateNo필드에 코드에 따라 no부여
        if(placeCateCode.equals("FD6")){
            searchMapDTO.setPlaceCateNo(1);
        } else {
            searchMapDTO.setPlaceCateNo(2);
        }
        return searchMapService.insertHotpalce(searchMapDTO);
    }

    // 2) mymap 테이블에 insert DB에 있지만 내 지도엔 없음
    //리턴: 방금입력된 map_no
    private int addMymap(Hotplace hotplace, User user, String inputMemo, char myRecommend) {
        SearchMapDTO searchMapDTO = new SearchMapDTO();
        searchMapDTO.setHotplaceNo(hotplace.getHotplaceNo());
        searchMapDTO.setInputMemo(inputMemo);
        searchMapDTO.setMyRecommend(myRecommend);
        return searchMapService.insertMyMap(user, hotplace.getHotplaceNo(), searchMapDTO);
    }

    //내지도에 저장된적 있는지 확인
    //저장됐으면 mymapNo를 리턴.
    private Integer checkRecord(Hotplace hotplace, User user) throws HotplaceNotFoundException{
        //MyBatis시도 -성공
        Map<String, Object> map = new HashMap<>();
        map.put("user_no", user.getUser_no());
        map.put("hotplace_no", hotplace.getHotplaceNo());
        return searchMapService.findHistory(map);
    }

    /*저장할 수 없다 페이지*/
    @GetMapping("/cantSave")
    public String cantSave(){
        return "/searchmap/cantSave";
    }
}
