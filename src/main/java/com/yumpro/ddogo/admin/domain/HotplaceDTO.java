package com.yumpro.ddogo.admin.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class HotplaceDTO {
    private int hotplace_no;
    private String sido;
    private String gugun;
    private String hotplace_name;
    private String address;
    private int hotplace_cate_no;
}
