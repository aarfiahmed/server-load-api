package com.app;

import com.app.service.ApiLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiLoadAppApplication implements CommandLineRunner {

	@Autowired
	ApiLoadService apiLoadService;

	public static void main(String[] args) {
		SpringApplication.run(ApiLoadAppApplication.class, args);
	}

	@Override
	public void run(String... args)  {
		//apiLoadService.callServiceByGetHttpMethod();
		apiLoadService.callServiceByPostHttpMethod();
	}
}
