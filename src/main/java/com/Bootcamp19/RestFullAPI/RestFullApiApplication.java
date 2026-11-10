package com.Bootcamp19.RestFullAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RestFullApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestFullApiApplication.class, args);
	}

}
