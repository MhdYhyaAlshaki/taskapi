package com.example.taskapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "file:./.env", ignoreResourceNotFound = true)
public class TaskapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskapiApplication.class, args);
	}

}
