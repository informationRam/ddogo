package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.admin.domain.ActiveUserDTO;
import com.yumpro.ddogo.admin.entity.Emoreview;
import com.yumpro.ddogo.admin.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmoreviewRepository extends JpaRepository<Emoreview, Integer> {
    @Query("SELECT DATE_FORMAT(u.recomDate, '%Y') AS month, select avg(e.emo_result) Emoreview e, Mymap m GROUP BY month")
    List<EmoAvgDTO> a();
    @Query("SELECT select avg(e.emo_result) FROM Emoreview e, Mymap m, User u group by u.gender")
    List<EmoAvgDTO> bc(String gender, int start, int finish);
    @Query("SELECT select avg(e.emo_result) Emoreview e, Mymap m, User u" +
            " WHERE u.gender = ?1 AND FUNCTION('timestampdiff', MONTH, u.BIRTH, now()) BETWEEN ?2 AND ?3")
    List<EmoAvgDTO> b(String gender, int start, int finish);
}
