package com.spring.batch.learnspringbatch.configuration.processor.compositeItemProcessor;

import org.springframework.batch.item.ItemProcessor;

public class FilterItemProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		if(Integer.valueOf(item) % 2 == 0) {
			return null;
		}
		return item;
	}

}
