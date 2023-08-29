package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.admin.domain.ActiveUserDTO;
import com.yumpro.ddogo.admin.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //현재 총회원수
    @Query("select count(u) from User u")
    int getUserTotal();

    //성별-나이에 따른 현재 회원수
    @Query("SELECT COUNT(u.user_no) FROM User u WHERE u.gender = ?1 AND FUNCTION('timestampdiff', MONTH, u.BIRTH, now()) BETWEEN ?2 AND ?3")
    int getUserTotalByAG(@Param("gender") String gender,@Param("start") int start,@Param("finish") int finish);

    //월별 가입자수
    @Query("SELECT DATE_FORMAT(u.joinDate, '%Y-%m') AS month, COUNT(u) AS userCount FROM User u GROUP BY month")
    List<ActiveUserDTO> monthlyJoin();

    //월별 액티브유저수
    @Query("SELECT DATE_FORMAT(u.recomDate, '%Y-%m') AS month, COUNT(DISTINCT m.user) AS activeUserCount FROM Mymap m GROUP BY month")
    List<ActiveUserDTO> monthlyActiveUser();

}
