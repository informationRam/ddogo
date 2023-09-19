package com.yumpro.ddogo.emoAnal.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class EmoService {

    //감정분석
    public double emoAnal(String review){
        double emo_result = 0;
        try {
            // API 엔드포인트 URL
            String url = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";

            // API 키 정보
            String apiKeyId = "edivdod8s8";
            String apiKey = "yDRlMYWzUFAW0LpcrsABRLikhHqMNtHof3pCasuI";

            // 요청 데이터 생성
            JSONObject data = new JSONObject();
            data.put("content",review);

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

                String jsonString = response.toString(); // json응답을 문자열로 바꾸기
                JSONObject result2 = new JSONObject(jsonString);

                // document객체 추출
                JSONObject document = result2.getJSONObject("document");

                // document로부터 감정과 신뢰도 추출
                JSONObject confidence = document.getJSONObject("confidence"); //감정
                float negativeConfidence = (float) confidence.getDouble("negative"); //긍정
                float neutralConfidence = (float) confidence.getDouble("neutral"); //부정
                float positiveConfidence = (float) confidence.getDouble("positive"); //중립
                emo_result = Math.round(positiveConfidence * 100) / 100.0; //소수점 둘째 자리까지 보여줌.

                //데이터 콘솔창 출력
                System.out.println("confidence="+confidence);
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
        return emo_result;
    }


}
