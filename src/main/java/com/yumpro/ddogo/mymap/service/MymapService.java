package com.yumpro.ddogo.mymap.service;

import com.yumpro.ddogo.common.entity.MyMap;
import com.yumpro.ddogo.mymap.common.DataNotFoundException;
import com.yumpro.ddogo.mymap.domain.MyMapDTO;
import com.yumpro.ddogo.mymap.mapper.MyMapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MymapService {


    private final MyMapMapper myMapMapper;
/*    private final MyMapRepository myMapRepository;*/




    //회원별 저장한 맛집 리스트 조회 - mapper
    public List<MyMapDTO> getHotplacesByUserNo(int userNo) {
        List<MyMapDTO> HotplList = myMapMapper.hotplacesByUserNo(userNo);
        return HotplList;
    }

    //저장한 맛집(마커) 삭제
      public MyMap deleteHotpl(Integer mapNo) {
          Optional<MyMap> myMap = myMapMapper.deleteMyHotpl(mapNo);
          if(myMap.isPresent()){
              return myMap.get();
          }else{
              throw new DataNotFoundException("mapNo not Found");
          }
      }
}

/*
    //회원별 저장한 맛집 리스트 조회 - repository버전
    public List<MyMapDTO> getSavedHotplaces(int userNo){
      List<MyMapDTO> myHotplList = mymapRepository.findHotplacesByUserNo(userNo);
      return myHotplList;
    }
*/


// 전체 db test용
//    public List<MymapMarkerDTO> getLatLngNames(){
//        List<MymapMarkerDTO> mymapMarkerDTOS = mymapHotplRepository.findLatLngNames();
//        return mymapMarkerDTOS;
//    }

