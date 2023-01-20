package com.example.kafkaexample.messaging.consumer;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.kafkaexample.messaging.serialize.GsonDeserializer;

public class ConsumerService<T> implements Closeable {
	
	private String kafkaAddress;
	private KafkaConsumer<String, T> consumer;
	private ConsumerFunction<T> parse;
	private ConsumerRecords<String, T> records;
	
	public ConsumerService(String kafkaAddress) {
		this.kafkaAddress = kafkaAddress;
	}
	
	public void configure(String groupId, Pattern topic, ConsumerFunction<T> parse, Class<T> type, Map<String, String> props) {
		this.parse = parse;
		this.consumer = new KafkaConsumer<>(properties(groupId, type, props));	
		consumer.subscribe(topic);
	}
	
	public void run() {
		while(true) {
			records = consumer.poll(Duration.ofMillis(100));
			
			if(!records.isEmpty()) {
				System.out.println("Encontrei registros.");
			}
			
			for (Iterator iterator = records.iterator(); iterator.hasNext();) {
				ConsumerRecord record = (ConsumerRecord) iterator.next();
				parse.consume(record);
			}
		}
	}
	
	private Properties properties(String groupId, Class<T> type, Map<String, String> props) {
		Properties properties = new Properties();
		
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		//properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, props.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getCanonicalName());
		properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, groupId + UUID.randomUUID().toString());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
		properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
		return properties;
	}

	@Override
	public void close() throws IOException {
		consumer.close();
	}
	
}
