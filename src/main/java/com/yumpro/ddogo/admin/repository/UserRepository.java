package com.yumpro.ddogo.admin.repository;

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

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //현재 총회원수
    @Query("select count(u) from User u")
    int getUserTotal();

    //성별-나이에 따른 현재 회원수
    @Query("SELECT COUNT(u.user_no) FROM User u WHERE u.gender = ?1 AND FUNCTION('timestampdiff', MONTH, u.BIRTH, now()) BETWEEN ?2 AND ?3")
    int getUserTotalByAG(@Param("gender") String gender,@Param("start") int start,@Param("finish") int finish);

    //연-월에 따른 액티브(mymap을 등록한) 회원수


}
