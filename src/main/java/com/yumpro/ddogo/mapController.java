package hello.hellospring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mapController {

    @GetMapping("/map")
    public String showMap(){

        return "kakaoMap";
    }
}
