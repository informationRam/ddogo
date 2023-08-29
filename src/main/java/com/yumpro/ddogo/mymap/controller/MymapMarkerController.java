package com.yumpro.ddogo.mymap.controller;

import com.yumpro.ddogo.mymap.dto.MymapMarkerDTO;
import com.yumpro.ddogo.mymap.repository.MymapMarkerRepository;
import com.yumpro.ddogo.mymap.service.MymapMarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class MymapMarkerController {

    private final MymapMarkerService mymapMarkerService;


    @GetMapping("/mymap")
    public String showMyMap(Model model){
        List<MymapMarkerDTO> mymapMarkerDTOS = mymapMarkerService.getLatLngNames();
        model.addAttribute("hotplaces",mymapMarkerDTOS); //타임리프로 view 데이터 전달("A",B)에서 A는 템플릿에서 사용할 변수명.B는 데이터담은 객체 리스트
        return "myMap/kakaoMap2";
    }
}
