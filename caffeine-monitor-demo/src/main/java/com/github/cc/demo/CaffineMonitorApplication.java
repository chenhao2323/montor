package com.github.cc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CaffineMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaffineMonitorApplication.class, args);
	}

}
