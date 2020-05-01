package com.example.accessingdatamysql;

import com.example.accessingdatamysql.security.AuthenticationFacade;
import com.example.accessingdatamysql.security.IAuthenticationFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@ComponentScan({ "com.example.accessingdatamysql.sprints",
		"com.example.accessingdatamysql.goals", "com.example.accessingdatamysql.users",
"com.example.accessingdatamysql.security"})
@SpringBootApplication(scanBasePackages = { "com.example.accessingdatamysql.sprints",
		"com.example.accessingdatamysql.goals", "com.example.accessingdatamysql.users",
		"com.example.accessingdatamysql.security"},
		exclude = { SecurityAutoConfiguration.class })
public class AccessingDataMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataMysqlApplication.class, args);
	}


}
