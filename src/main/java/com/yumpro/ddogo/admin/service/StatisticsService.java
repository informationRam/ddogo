package com.yumpro.ddogo.admin.service;


import com.yumpro.ddogo.admin.repository.StatisticsRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {
    StatisticsRepository statisticsRepository;
    public int getUserTotal() {
        int total = statisticsRepository.getUserTotal();
        return total;
    }
}
