package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.admin.entity.Hotplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HotplaceRepository extends JpaRepository<Hotplace, Integer> {
    //맛집 랭킹
    @Query("SELECT hotplace_no FROM hotplace WHERE hotplace_no IN (SELECT hotplace_no FROM mymap WHERE recom='Y' GROUP BY hotplace_no ORDER BY COUNT(recom) DESC)")
    int getUserTotal();

}
