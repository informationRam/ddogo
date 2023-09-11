package com.yumpro.ddogo.main.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BestJjimDTO {

    private int hotplace_no;
    private int map_no;
    private String hotplace_name;
    private double emo_result;
    private double avg_emo_result;
    private int jjim;
    private String address;
}
