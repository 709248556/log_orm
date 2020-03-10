package com.example.log_orm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan("com.example.log_orm")
@SpringBootApplication
public class LogOrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogOrmApplication.class, args);
	}

}
