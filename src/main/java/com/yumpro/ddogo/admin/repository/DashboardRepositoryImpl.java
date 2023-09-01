package com.yumpro.ddogo.admin.repository;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DashboardRepositoryImpl implements DashboardRepository{
    private final SqlSession sqlSession;

    @Override
    public int getUserTotal() throws DataAccessException{
        return sqlSession.selectOne("admin.getUserTotal");
    }

    @Override
    public int getRecentUser() throws DataAccessException{
        return sqlSession.selectOne("admin.getRecentUser");
    }
    @Override
    public List<HashMap<String,Object>> hotplaceRank() throws DataAccessException{
        return sqlSession.selectList("admin.hotplaceRank");
    }

    @Override
    public int newPlaceCnt() throws DataAccessException{
        return sqlSession.selectOne("admin.newPlaceCnt");
    }
    @Override
    public int hotplaceTotal() throws DataAccessException{
        return sqlSession.selectOne("admin.hotplaceTotal");
    }

    @Override
    public double emoAvg() throws DataAccessException{
        return sqlSession.selectOne("admin.emoAvg");
    }
    @Override
    public double RecentEmoAvg() throws DataAccessException{
        return sqlSession.selectOne("admin.RecentEmoAvg");
    }

    public int monthlyActiveUser(int monthlyGab) throws DataAccessException{
        return sqlSession.selectOne("admin.monthlyActiveUser", monthlyGab);
    }

}
