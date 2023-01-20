package com.example.kafkaexample.messaging.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

public class NewMessageCallBack implements Callback {
	
	public void onCompletion(RecordMetadata metadata, Exception exception) {
		if(exception != null) {
			System.out.println(exception.getMessage());
			return;
		}
		System.out.println("Enviado com sucesso: topic:" + metadata.topic() + " - partition: " + metadata.partition());
	}
}