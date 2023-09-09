package com.yumpro.ddogo.emoAnal.service;


        import com.yumpro.ddogo.emoAnal.entity.Emoreview;
        import com.yumpro.ddogo.emoAnal.repository.EmoRepository;
        import lombok.RequiredArgsConstructor;
        import org.json.JSONObject;
        import org.springframework.stereotype.Service;

        import java.io.OutputStream;
        import java.net.HttpURLConnection;
        import java.net.URL;

@Service
@RequiredArgsConstructor
public class EmoService {

    private final EmoRepository emoRepository;

    //리뷰등록(임시)
    public void updateReview(String reveiw,int hotplace_no,int map_no,double emo_result){
        Emoreview emoreview = new Emoreview();
        emoreview.setReview(reveiw);
        emoreview.setHotplace_no(hotplace_no);
        emoreview.setMap_no(map_no);
        emoreview.setEmo_result(emo_result);
        emoRepository.save(emoreview);
    }

    //감정분석

    //감정분석
    public double emoAnal(String reveiw){
        double emo_result = 0;
        try {
            // API 엔드포인트 URL
            String url = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";

            // API 키 정보
            String apiKeyId = "edivdod8s8";
            String apiKey = "yDRlMYWzUFAW0LpcrsABRLikhHqMNtHof3pCasuI";

            // 요청 데이터 생성
            JSONObject data = new JSONObject();
            data.put("content",reveiw);

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
        return emo_result;
    }


}