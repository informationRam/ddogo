package com.yumpro.ddogo.admin.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class RankingDTO {
    private int hotplace_no;
    private String sido;
    private String gugun;
    private String hotplace_name;
    private String hotplace_cate_name;
    private int recom_count;
    private double emo_result;
}
