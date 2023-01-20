package com.example.kafkaexample.messaging.serialize.dto;

import java.io.Serializable;

public class DataDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String someData;
	
	public DataDTO() {
		super();
	}

	public DataDTO(String someData) {
		super();
		this.someData = someData;
	}

	public String getSomeData() {
		return someData;
	}

	public void setSomeData(String someData) {
		this.someData = someData;
	}
	
}
