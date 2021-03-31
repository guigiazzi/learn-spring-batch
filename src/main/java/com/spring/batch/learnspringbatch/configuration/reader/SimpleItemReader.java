package com.spring.batch.learnspringbatch.configuration.reader;

import java.util.Iterator;
import java.util.List;

import org.springframework.batch.item.ItemReader;

public class SimpleItemReader implements ItemReader<String> {

	private final Iterator<String> data;

	public SimpleItemReader(List<String> data) {
		this.data = data.iterator();
	}

	@Override
	public String read() throws Exception {
		if (this.data.hasNext()) {
			return this.data.next();
		}
		return null;
	}

}
