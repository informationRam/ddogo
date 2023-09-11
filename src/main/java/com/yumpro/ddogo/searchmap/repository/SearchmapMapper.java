package com.yumpro.ddogo.searchmap.repository;

import com.yumpro.ddogo.common.entity.Hotplace;
import com.yumpro.ddogo.common.entity.MyMap;
import com.yumpro.ddogo.searchmap.dto.HistoryDTO;
import com.yumpro.ddogo.searchmap.dto.HotplaceDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class SearchmapMapper {
    @Autowired
    private SqlSession sqlSession;

    public MyMap hasHistory(HistoryDTO historyDTO) {
        return sqlSession.selectOne("searchmap.findHistory", historyDTO); //insert성공시, 1을 리턴.
    }

    public Hotplace selectByLatLng(HotplaceDTO hotplaceDTO){
        return sqlSession.selectOne("searchmap.selectByLatLng", hotplaceDTO);
    }
}
