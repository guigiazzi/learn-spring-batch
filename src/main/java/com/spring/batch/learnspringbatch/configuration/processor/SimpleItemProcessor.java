package com.spring.batch.learnspringbatch.configuration.processor;

import org.springframework.batch.item.ItemProcessor;

public class SimpleItemProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		return "The item " + item + " has been processed";
	}

}
