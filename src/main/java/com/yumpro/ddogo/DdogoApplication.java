package com.yumpro.ddogo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DdogoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DdogoApplication.class, args);
	}
}