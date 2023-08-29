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

    @GetMapping("/reviewadd")
    public String add(ReviewForm ReviewForm) {
        return "emoAnal/emoReviewForm";
    }

    @PostMapping("/reviewadd")
    public String emoreview(@Valid ReviewForm reviewForm, BindingResult bindingResult, Emoreview emoreview) {

        double emo_result = 0;
        try {
            // API 엔드포인트 URL
            String url = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";

            // API 키 정보
            String apiKeyId = "edivdod8s8";
            String apiKey = "yDRlMYWzUFAW0LpcrsABRLikhHqMNtHof3pCasuI";

            // 요청 데이터 생성
            JSONObject data = new JSONObject();
            data.put("content", reviewForm.getReview());

            // POST 요청 설정
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", apiKeyId);
            connection.setRequestProperty("X-NCP-APIGW-API-KEY", apiKey);
            connection.setDoOutput(true);

            // 요청 데이터 전송
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = data.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // 응답 읽기
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder response = new StringBuilder();
                try (java.io.BufferedReader in = new java.io.BufferedReader(
                        new java.io.InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                }
                //JSONObject result = new JSONObject(response.toString());
                String jsonString = response.toString(); // Replace with your JSON response string
                JSONObject result2 = new JSONObject(jsonString);

                // Extracting document object
                JSONObject document = result2.getJSONObject("document");

                // Extracting sentiment and confidence from the document object
                String sentiment = document.getString("sentiment");
                JSONObject confidence = document.getJSONObject("confidence");
                float negativeConfidence = (float) confidence.getDouble("negative");
                float neutralConfidence = (float) confidence.getDouble("neutral");
                float positiveConfidence = (float) confidence.getDouble("positive");
                emo_result = Math.round(positiveConfidence * 100) / 100.0; //소수점 둘째자리까지 보여줌.

                // Printing extracted data
                System.out.println("Document Sentiment: " + sentiment);
                System.out.println("Negative Confidence: " + negativeConfidence);
                System.out.println("Neutral Confidence: " + neutralConfidence);
                System.out.println("Positive Confidence: " + positiveConfidence);
                System.out.println("결과=" + emo_result);

            } else {
                System.out.println("요청 실패 - 상태 코드: " + responseCode);
                try (java.io.BufferedReader errorIn = new java.io.BufferedReader(
                        new java.io.InputStreamReader(connection.getErrorStream()))) {
                    String line;
                    StringBuilder errorMsg = new StringBuilder();
                    while ((line = errorIn.readLine()) != null) {
                        errorMsg.append(line);
                    }
                    System.out.println("에러 메시지: " + errorMsg.toString());
                }
            }

            // 연결 종료
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bindingResult.hasErrors()) { //에러가 존재하면
            return "emoAnal/emoReviewForm";  //emoReviewForm.html문서로 이동
        }

        emoService.updateReview(reviewForm.getReview(), reviewForm.getHotplace_no(), reviewForm.getMap_no(), emo_result);
        return "emoAnal/imsi"; //리뷰등록 성공하면
    }

}
