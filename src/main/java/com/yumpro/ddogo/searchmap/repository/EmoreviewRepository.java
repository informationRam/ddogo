package com.yumpro.ddogo.searchmap.repository;

import com.yumpro.ddogo.common.entity.EmoReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmoreviewRepository extends JpaRepository<EmoReview, Integer> {
}
