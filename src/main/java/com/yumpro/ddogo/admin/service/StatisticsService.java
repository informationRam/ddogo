package com.yumpro.ddogo.admin.service;


import com.yumpro.ddogo.admin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;

    public int getUserTotal() {
        return userRepository.getUserTotal();
    }

    public int getUserTotalByAG(String gender,int start,int finish) {
        return userRepository.getUserTotalByAG(gender,start,finish);
    }
}
