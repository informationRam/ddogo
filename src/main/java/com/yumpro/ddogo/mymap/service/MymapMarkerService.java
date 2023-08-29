package com.yumpro.ddogo.mymap.service;

import com.yumpro.ddogo.mymap.dto.MymapMarkerDTO;
import com.yumpro.ddogo.mymap.repository.MymapMarkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MymapMarkerService {

    private final MymapMarkerRepository mymapHotplRepository;

    //DTO 를 Entity로 변환

    public List<MymapMarkerDTO> getLatLngNames(){
        List<MymapMarkerDTO> mymapMarkerDTOS = mymapHotplRepository.findLatLngNames();
        return mymapMarkerDTOS;
    }

}
