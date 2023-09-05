package com.yumpro.ddogo.searchmap.repository;

import com.yumpro.ddogo.common.entity.Hotplace;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotplaceRepository extends JpaRepository<Hotplace, Integer> {

    //Hotplace findByLatAndLng(double lat, double lng);

}
