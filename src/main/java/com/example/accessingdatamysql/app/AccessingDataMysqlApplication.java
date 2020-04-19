package com.example.accessingdatamysql.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan({ "com.example.accessingdatamysql.sprints",
		"com.example.accessingdatamysql.goals", "com.example.accessingdatamysql.users"})
@SpringBootApplication(scanBasePackages = { "com.example.accessingdatamysql.sprints",
		"com.example.accessingdatamysql.goals", "com.example.accessingdatamysql.users"})
public class AccessingDataMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataMysqlApplication.class, args);
	}
}
