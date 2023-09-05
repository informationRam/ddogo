package com.yumpro.ddogo.searchmap.repository;

import com.yumpro.ddogo.common.entity.MyMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MymapRepository extends JpaRepository<MyMap, Integer> {

}
