package com.yumpro.ddogo.admin.repository;

import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;

public interface DashboardRepository {
    int getUserTotal() throws DataAccessException;

    int getRecentUser() throws DataAccessException;

    List<HashMap<String,Object>> hotplaceRank() throws DataAccessException;

    int newPlaceCnt() throws DataAccessException;

    int hotplaceTotal() throws DataAccessException;

    double emoAvg() throws DataAccessException;

    double RecentEmoAvg() throws DataAccessException;

    int nowActiveUser() throws DataAccessException;

    List<HashMap<String, Object>> localHotplaceCnt() throws DataAccessException;

    int notSolvedCnt() throws DataAccessException;
}
