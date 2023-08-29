package com.yumpro.ddogo.mymap.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

//조회결과 담기
public class MymapMarkerDTO {

    private String hotplaceName;
    private double lat;
    private double lng;

}
