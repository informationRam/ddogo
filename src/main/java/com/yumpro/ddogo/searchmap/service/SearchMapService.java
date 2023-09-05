package com.yumpro.ddogo.searchmap.service;

import com.yumpro.ddogo.common.entity.*;
import com.yumpro.ddogo.searchmap.dto.HistoryDTO;
import com.yumpro.ddogo.searchmap.dto.HotplaceDTO;
import com.yumpro.ddogo.searchmap.dto.SearchMapDTO;
import com.yumpro.ddogo.searchmap.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//@RequiredArgsConstructor
@Service
public class SearchMapService {

    private final HotplaceRepository hotplaceRepository;
    private final HotplaceCateRepository hotplaceCateRepository;
    private final MymapRepository mymapRepository;
    private final EmoreviewRepository emoreviewRepository;
    private final SearchmapMapper mapper;

    @Autowired
    public SearchMapService(HotplaceRepository hotplaceRepository,
                            HotplaceCateRepository hotplaceCateRepository,
                            MymapRepository mymapRepository,
                            EmoreviewRepository emoreviewRepository,
                            SearchmapMapper mapper) {
        this.hotplaceRepository = hotplaceRepository;
        this.hotplaceCateRepository = hotplaceCateRepository;
        this.mymapRepository = mymapRepository;
        this.emoreviewRepository = emoreviewRepository;
        this.mapper = mapper;
    }

    // 1) hotplace 테이블에 insert
    //  리턴: 방금 hotplace에 입력된 pk인 hotplace_no의 값
    public int insertHotpalce(SearchMapDTO searchMapDTO) {
        Hotplace hotplace = new Hotplace();
        //hotplace.setHotplaceCate(searchMapDTO.getPlaceCateNo());
        //hotplace.setHotplaceNo((Hotplace)hotplaceRepository.findById(searchMapDTO.getPlaceCateNo()));
        HotplaceCate hotplaceCate = (HotplaceCate) hotplaceCateRepository.findById(searchMapDTO.getPlaceCateNo()).orElse(null);
        if (hotplaceCate != null) {
            hotplace.setHotplaceCate(hotplaceCate); // HotplaceCate 엔티티 설정
        }
        hotplace.setHotplaceName(searchMapDTO.getPlaceName());
        hotplace.setLat(searchMapDTO.getLat());
        hotplace.setLng(searchMapDTO.getLng());
        hotplace.setSido(searchMapDTO.getSido());
        hotplace.setGugun(searchMapDTO.getGugun());
        hotplace.setAddress(searchMapDTO.getAddress());
        return hotplaceRepository.save(hotplace).getHotplaceNo();
    }

    // 2) mymap 테이블에 insert
    //  리턴: 방금 mymap에 입력된 pk인 map_no의 값
    public int insertMyMap(User user , int hotplaceNo, SearchMapDTO searchMapDTO) {
        MyMap myMap = new MyMap();
        myMap.setHotplaceNo((Hotplace) hotplaceRepository.findById(hotplaceNo).orElse(null));
        myMap.setUserNo(user);
        myMap.setRecom(searchMapDTO.getMyRecommend());
        myMap.setRecomDate(LocalDateTime.now());
        myMap.setMapMemo(searchMapDTO.getInputMemo());
        return mymapRepository.save(myMap).getMapNo();
    }

    // 4) 분설결과와 함께 emoreview 테이블에 insert
    // 파라미터: 분석결과, 방금 입력한 hotplace_no, 방금 입력한 map_no
    public void insertEmoReview(double emoResult, int hotplaceNo, int mapNo, SearchMapDTO searchMapDTO) {
        EmoReview emoReview = new EmoReview();
        emoReview.setMymap(mymapRepository.findById(mapNo).orElse(null));
        emoReview.setHotplace(hotplaceRepository.findById(hotplaceNo).orElse(null));
        emoReview.setReview(searchMapDTO.getInputReview());
        emoReview.setEmoResult(emoResult);
        emoreviewRepository.save(emoReview);
        System.out.println("emoReview입력 완");
    }

    /* 해당 핫플레이스 번호와 유저 번호가 같은 기록이 mymap에 있는지 확인
    // 있으면 중복하여 저장할 수 없도록.
    public boolean hasHistory(User user, SearchMapDTO deliverHotplace){
        //return mymapRepository.findBy();
        MyMap myMap = searchMapMapper.hasHistory(user.getUser_no(), deliverHotplace.getHotplaceNo());
        System.out.println("mapNo:"+myMap.getMapNo());
        if(myMap!=null){
            return true;
        } else {
            return false;
        }
    }*/

    // 해당 핫플레이스 번호와 유저 번호가 같은 기록이 mymap에 있는지 확인
    public SearchMapDTO findHistory(User user, SearchMapDTO hotplaceResult) {
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setUserNo(user.getUser_no());
        historyDTO.setHotplaceNo(hotplaceResult.getHotplaceNo());
        return deliverMMNo(mapper.hasHistory(historyDTO));
        //둘다 인티저

    }

    // 해당 좌표로 저장된 장소가 있는지 확인
    /*public SearchMapDTO findByLatAndLng(double lat, double lng) {
        HotplaceDTO hotplaceDTO = new HotplaceDTO();
        hotplaceDTO.setLat(lat);
        hotplaceDTO.setLng(lng);
        return deliverNo(mapper.selectByLatLng(hotplaceDTO));
    }*/
    public SearchMapDTO findByLatAndLng(double lat, double lng) {
        HotplaceDTO hotplaceDTO = new HotplaceDTO();
        hotplaceDTO.setLat(lat);
        hotplaceDTO.setLng(lng);
        return deliverNo(mapper.selectByLatLng(hotplaceDTO));
    }

    //맛집번호 DTO로 변환
    public SearchMapDTO deliverNo (Hotplace hotplace){ //hotplace가 null??
        SearchMapDTO searchMapDTO = new SearchMapDTO();
        searchMapDTO.setHotplaceNo(hotplace.getHotplaceNo());
        return searchMapDTO;
    }

    //mymap DTO로 변환
    public SearchMapDTO deliverMMNo (MyMap myMap){
        SearchMapDTO searchMapDTO = new SearchMapDTO();
        searchMapDTO.setMapNo(myMap.getMapNo());
        return searchMapDTO;
    }


    //MyMap hasHistory(Integer userNo, Integer hotplaceNo);
}
