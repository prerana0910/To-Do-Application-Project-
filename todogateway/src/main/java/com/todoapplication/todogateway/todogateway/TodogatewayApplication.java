package com.todoapplication.todogateway.todogateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TodogatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodogatewayApplication.class, args);
	}

}
