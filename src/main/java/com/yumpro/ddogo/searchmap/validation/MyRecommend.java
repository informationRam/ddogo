package com.yumpro.ddogo.searchmap.validation;

public enum MyRecommend {
    Y("또갈지도"), N("안갈지도");

    private final String desc;

    MyRecommend(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
