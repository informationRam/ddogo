package com.yumpro.ddogo.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ActiveUsers")
@Getter
@Setter
@RequiredArgsConstructor
public class ActiveUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer month;

    @Column(name = "active_user_cnt", nullable = false)
    private Integer activeUserCnt;
}
