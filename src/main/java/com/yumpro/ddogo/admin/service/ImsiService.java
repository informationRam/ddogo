package com.yumpro.ddogo.admin.service;

import com.yumpro.ddogo.admin.repository.ImsiRepository;
import org.springframework.stereotype.Service;

@Service
public class ImsiService {
    private ImsiRepository imsiRepository;
    public int getTotalMemberCNT() {
        int totalMemberCNT = imsiRepository.selectTotalMemberCNT();//dao의 메서드호출
        return totalMemberCNT;
    }
}
