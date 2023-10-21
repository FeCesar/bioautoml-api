package com.bioautoml;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableRabbit
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class BioautomlApplication {

	public static void main(String[] args) {
		SpringApplication.run(BioautomlApplication.class, args);
	}

}
