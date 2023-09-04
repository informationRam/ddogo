package com.yumpro.ddogo.searchmap.repository;

import com.yumpro.ddogo.common.entity.Hotplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotplaceRepository extends JpaRepository<Hotplace, Integer> {
    //public void testInsert(SearchMapDTO searchMapDTO); extends JpaRepository<SearchMapDTO, Long>

}
