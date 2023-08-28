package com.yumpro.ddogo.admin.service;


import com.yumpro.ddogo.admin.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsRepository statisticsRepository;
    public int getUserTotal() {
        return statisticsRepository.getUserTotal();
    }
}
