package com.yumpro.ddogo.emoAnal.controller;

import com.yumpro.ddogo.emoAnal.entity.Emoreview;
import com.yumpro.ddogo.emoAnal.service.EmoService;
import com.yumpro.ddogo.emoAnal.validation.ReviewForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@SpringBootApplication
@RequiredArgsConstructor
@RequestMapping("/emo")
@Controller
public class EmoController {

    private final EmoService emoService;

    //리뷰 폼 보여주기
    @GetMapping("/reviewadd")
    public String add(ReviewForm ReviewForm) {

        return "emoAnal/emoReviewForm";
    }


    //리뷰 등록 처리(감정분석과 함께)
    @PostMapping("/reviewadd")
    public String emoreview(@Valid ReviewForm reviewForm, BindingResult bindingResult) {
        //1.파라미터받기
        //2.비지니스로직수행
        //2-1.감정분석 수행
        System.out.println("review="+reviewForm.getReview());
        double emo_result = emoService.emoAnal(reviewForm.getReview());
        if (bindingResult.hasErrors()) { //에러가 존재하면
            return "emoAnal/emoReviewForm";  //emoReviewForm.html문서로 이동
        }
        //2-2.리뷰등록
        emoService.updateReview(reviewForm.getReview(), reviewForm.getHotplace_no(), reviewForm.getMap_no(), emo_result);

        //3.Model //4.View
        return "emoAnal/imsi"; //리뷰등록 성공하면
    }

}
