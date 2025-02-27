package com.workfall.jwt_checking;

import com.workfall.jwt_checking.service.MasterRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtCheckingApplication {

	private static final Logger log = LoggerFactory
			.getLogger(JwtCheckingApplication.class);

    public static void main(String[] args) {
		SpringApplication.run(JwtCheckingApplication.class, args);
		log.info("Hello Abhishek... ");
	}

}
