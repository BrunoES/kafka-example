package com.example.kafkaexample.messaging.producer;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.kafkaexample.messaging.serialize.GsonSerializer;

public class ProducerService<T> implements Closeable {

	private final KafkaProducer<String, T> producer;
	private String kafkaAddress;
	
	public ProducerService(String kafkaAddress) {
		this.kafkaAddress = kafkaAddress;
		this.producer = new KafkaProducer<String, T>(properties()); 
	}

	public Properties properties() {
		Properties properties = new Properties();
		
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
				
		return properties;
	}
	
	public void send(String queue, String messageId, T objectMessage) throws InterruptedException, ExecutionException {
		ProducerRecord<String, T> record = new ProducerRecord<String, T>(queue, messageId, objectMessage);
		producer.send(record, new NewMessageCallBack()).get();
	}
	
	@Override
	public void close() throws IOException {
		producer.close();
	}

}