package com.yumpro.ddogo.searchmap.repository;

import com.yumpro.ddogo.common.entity.MyMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MymapRepository extends JpaRepository<MyMap, Integer> {

    //MyMap findByUserNoAndHotplaceNo(Integer userNo, Integer hotplaceNo);
}
