package com.challenge2.challenge2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.challenge2.challenge2.entities")
public class Challenge2Application {

	public static void main(String[] args) {

        SpringApplication.run(Challenge2Application.class, args);
	}
}
