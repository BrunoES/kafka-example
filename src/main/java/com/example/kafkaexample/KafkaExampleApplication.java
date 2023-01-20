package com.example.kafkaexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class KafkaExampleApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaExampleApplication.class, args);
		
		//TestService testService = new TestService();
	/*	DataDTO data = new DataDTO("Some String ...");
		
		try {
			testService.testProducer(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		testService.testConsumer();*/
	}

}