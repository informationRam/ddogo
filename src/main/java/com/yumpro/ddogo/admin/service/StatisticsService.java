package com.yumpro.ddogo.admin.service;


import com.yumpro.ddogo.admin.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;

    public Integer getUserTotal() {
        return statisticsRepository.getUserCnt();
    }
}
