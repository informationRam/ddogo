package com.yumpro.ddogo.admin.service;

import com.yumpro.ddogo.admin.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DashboardRepository dashboardRepository;

    public int getUserTotal(){
        return dashboardRepository.getUserTotal();
    }

    public int getRecentUser() {
        return dashboardRepository.getRecentUser();
    }

    public List<HashMap<String,Object>> hotplaceRank() {
        return dashboardRepository.hotplaceRank();
    }

    public int newPlaceCnt() {
        return dashboardRepository.newPlaceCnt();
    }
    public int hotplaceTotal() {
        return dashboardRepository.hotplaceTotal();
    }

    public double emoAvg() {
        return dashboardRepository.emoAvg();
    }

    public double RecentEmoAvg() {
        return dashboardRepository.RecentEmoAvg();
    }

    public int monthlyActiveUser(int monthlyGab) {
        return dashboardRepository.monthlyActiveUser(monthlyGab);
    }
    public int yearlyActiveUser(int yearlyGab) {
        return dashboardRepository.yearlyActiveUser(yearlyGab);
    }
}
