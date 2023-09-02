package com.yumpro.ddogo.admin.service;

import com.yumpro.ddogo.admin.entity.ActiveUser;
import com.yumpro.ddogo.admin.repository.ActiveUserRepository;
import com.yumpro.ddogo.admin.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final DashboardRepository dashboardRepository;
    private final ActiveUserRepository activeUserRepository;

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

    public int nowActiveUser() {
        return dashboardRepository.nowActiveUser();
    }
    @Scheduled(cron = "0 0 0 1 * ?") // 매월 1일 0시 0분에 실행
    public void saveMonthlyActiveUSer() {
        ActiveUser activeUser = new ActiveUser();
        activeUser.setYear(LocalDate.now().minusDays(1).getYear());
        activeUser.setMonth(LocalDate.now().minusDays(1).getMonthValue());
        activeUser.setActiveUserCnt(dashboardRepository.nowActiveUser());

        activeUserRepository.save(activeUser);
    }
}
