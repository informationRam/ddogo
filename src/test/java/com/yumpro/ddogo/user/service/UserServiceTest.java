package com.yumpro.ddogo.user.service;

import com.yumpro.ddogo.common.entity.User;
import com.yumpro.ddogo.user.reprository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {


    @Autowired
    private UserRepository userRepository;


    @Test
    void userJoin() {

    }




    @Test
    void usertest() {
        Optional<User> test1 = userRepository.findByUserId("test1");


        if (test1.isEmpty()) {
            System.out.println("실패"); // User not found
            // Add assertions for test failure
        } else {
            User user = test1.get();
            System.out.println(user.getPwd());
            System.out.println("성공");
            // Add assertions for test success
        }
    }

}