package com.yumpro.ddogo.admin.repository;

import org.springframework.dao.DataAccessException;

import java.util.HashMap;
import java.util.List;

public interface DashboardRepository {
    public int getUserTotal() throws DataAccessException;

    public int getRecentUser() throws DataAccessException;

    public List<HashMap<String,Object>> hotplaceRank() throws DataAccessException;

    public int newPlaceCnt() throws DataAccessException;

    public int hotplaceTotal() throws DataAccessException;
}
