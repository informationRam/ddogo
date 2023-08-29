package com.yumpro.ddogo.emoAnal.service;

import com.yumpro.ddogo.emoAnal.entity.Emoreview;
import com.yumpro.ddogo.emoAnal.repository.EmoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmoService {

    private final EmoRepository emoRepository;

    public void updateReview(String reveiw,int hotplace_no,int map_no,double emo_result){
        Emoreview emoreview = new Emoreview();
        emoreview.setReview(reveiw);
        emoreview.setHotplace_no(hotplace_no);
        emoreview.setMap_no(map_no);
        emoreview.setEmo_result(emo_result);
        emoRepository.save(emoreview);
    }

    public void add(String reveiw,int hotplace_no,int map_no){
        Emoreview emoreview = new Emoreview();
        emoreview.setReview(reveiw);
        emoreview.setHotplace_no(hotplace_no);
        emoreview.setMap_no(map_no);
        emoRepository.save(emoreview);
    }
}
