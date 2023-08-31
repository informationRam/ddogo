package com.yumpro.ddogo.admin.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class HotplaceDTO {
    private int hotplace_no;
    private String sido;
    private String gugun;
    private String hotplace_name;
    private String address;
    private int hotplace_cate_no;
    private double lat;
    private double lng;
}
