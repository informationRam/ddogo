package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.admin.entity.ActiveUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActiveUserRepository extends JpaRepository<ActiveUser,Integer> {

}
