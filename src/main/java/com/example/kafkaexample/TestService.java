package com.example.kafkaexample;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.kafkaexample.messaging.consumer.ConsumerService;
import com.example.kafkaexample.messaging.producer.ProducerService;
import com.example.kafkaexample.messaging.serialize.dto.DataDTO;

import jakarta.annotation.PostConstruct;

@Component
public class TestService {
	
	@Value("${kafka.address:localhost:9092}")
	private String kafkaAdress;
	
	private ProducerService<DataDTO> producer;
	private ConsumerService<DataDTO> consumer;
	
	@PostConstruct
	private void configure() {
		producer = new ProducerService<>(kafkaAdress);
		consumer = new ConsumerService<>(kafkaAdress);
		
		DataDTO data = new DataDTO("Some String ...");
		testProducer(data);
		testConsumer();
	}

	public TestService() {
		super();
	}

	public void testProducer(DataDTO data) {
		String key = UUID.randomUUID().toString();
		try {
			producer.send("KAFKA-EXAMPLE.QUEUE", key, data);
			System.out.println("Mensagem " + key + " adicionada à fila.");
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public void testConsumer() {
		try {
			consumer.configure(KafkaExampleApplication.class.getSimpleName(),
					Pattern.compile("KAFKA-EXAMPLE.*"),
					this::parse,
					DataDTO.class,
					Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()));
			consumer.run();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void parse(ConsumerRecord<String, DataDTO> record) {
		System.out.println("Consumindo mensagem " + record.topic());
		System.out.println(record.key());
		System.out.println(record.value());
		System.out.println(record.partition());
		System.out.println(record.offset());
	}
}
