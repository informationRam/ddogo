package com.yumpro.ddogo.searchmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchMapController {
    @GetMapping("/search")
    public String showMap(){
        return "searchmap/testSearchMap";
    }
}
