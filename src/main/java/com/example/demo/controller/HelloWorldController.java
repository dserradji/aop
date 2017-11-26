package com.example.demo.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.aop.annotation.Loggable;
import com.example.demo.aop.annotation.Measurable;

@RestController
public class HelloWorldController {

	public static final String SPLUNK_KEY = "hello.world.controller";
	
	@Autowired
	private String message;
	
	@Measurable
	@Loggable(SPLUNK_KEY)
	@GetMapping
	public String sayHello(@RequestParam("name") String param) {
		int nbr = ThreadLocalRandom.current().nextInt(2);
		if (nbr == 1) {
			throw new RuntimeException("Oops, shit happened");
		}
		return message;
	}
}
