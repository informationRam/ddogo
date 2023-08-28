package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.admin.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatisticsRepository  extends JpaRepository<User, Integer> {
    @Query("SELECT COUNT(u) FROM User u")
    Integer getUserCnt();
}
