package com.example.demo.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
		"com.example.demo.models",
		"com.example.demo.client",
		"com.example.demo.cli"
})
public class HotelRestClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelRestClientApplication.class, args);
	}

}