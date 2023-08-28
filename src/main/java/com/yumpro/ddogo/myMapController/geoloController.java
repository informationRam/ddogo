package com.yumpro.ddogo.myMapController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class geoloController {
    @GetMapping("/gt")
    public String currentLocation(){

        return "myMap/geoloTest";
    }
}
