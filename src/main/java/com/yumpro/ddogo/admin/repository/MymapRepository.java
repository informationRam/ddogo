package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.admin.domain.ActiveUserDTO;
import com.yumpro.ddogo.admin.entity.Mymap;
import com.yumpro.ddogo.admin.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MymapRepository extends JpaRepository<Mymap, Integer> {
    //연도별 액티브유저수
    @Query("SELECT DATE_FORMAT(u.recomDate, '%Y') AS month, COUNT(DISTINCT m.user) AS activeUserCount FROM Mymap m GROUP BY month")
    List<ActiveUserDTO> yearlyActiveUser(); 
    //월별 액티브유저수
    @Query("SELECT DATE_FORMAT(u.recomDate, '%Y-%m') AS month, COUNT(DISTINCT m.user) AS activeUserCount FROM Mymap m GROUP BY month")
    List<ActiveUserDTO> monthlyActiveUser();

}
