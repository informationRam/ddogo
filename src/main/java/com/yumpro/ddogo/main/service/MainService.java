package com.yumpro.ddogo.main.service;

import com.yumpro.ddogo.main.repository.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MainService {
    @Autowired
    private MainRepository mainRepository;


    public List<HashMap<String, Object>> eatjjim() throws Exception{
        List<HashMap<String, Object>> eatjjimList = mainRepository.eatjjim();
        return eatjjimList;
    }

    public List<HashMap<String, Object>> cafejjim() throws Exception{
        List<HashMap<String, Object>> cafejimList = mainRepository.cafejjim();
        return cafejimList;
    }

    public Map<String, List<String>> getsidogungu() {
        Map<String, List<String>> sigunguMap = new HashMap<>();
        List<String> sidoList = mainRepository.getSelectList();

        for (String sido : sidoList) {
            List<String> sigunguList = mainRepository.gugunList(sido);
            sigunguMap.put(sido, sigunguList);
        }

        return sigunguMap;
    }


    public List<HashMap<String, Object>> monthBest(String sido, String gugugn,int hotplace_cate_no) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sido", sido);
        paramMap.put("gugun", gugugn);
        paramMap.put("hotplace_cate_no", hotplace_cate_no);

        return mainRepository.monthBest(paramMap);
    }



}
