package com.yumpro.ddogo.mymap.repository;

import com.yumpro.ddogo.common.entity.MyMap;
import org.springframework.data.jpa.repository.JpaRepository;

// <Repository의 대상이 되는 Entity타입,  Entity타입의 PK타입>
public interface MyMapRepository extends JpaRepository<MyMap,Integer> {



}