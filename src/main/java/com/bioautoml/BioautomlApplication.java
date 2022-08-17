package com.bioautoml;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class BioautomlApplication {

	public static void main(String[] args) {
		SpringApplication.run(BioautomlApplication.class, args);
	}

}
