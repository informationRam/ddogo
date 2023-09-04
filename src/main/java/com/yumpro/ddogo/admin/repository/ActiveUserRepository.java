package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.common.entity.ActiveUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ActiveUserRepository extends JpaRepository<ActiveUser,Integer> {

    Optional<List<ActiveUser>> findByYear(int year);
}
