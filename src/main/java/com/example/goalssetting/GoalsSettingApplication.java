package com.example.goalssetting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(scanBasePackages = {"com.example.goalssetting.controllers",
		"com.example.goalssetting.security", "com.example.goalssetting.config"},
		exclude = { SecurityAutoConfiguration.class })
public class GoalsSettingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoalsSettingApplication.class, args);
	}


}
