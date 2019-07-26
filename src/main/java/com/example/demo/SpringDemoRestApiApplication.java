package com.example.demo;

import java.util.stream.IntStream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.events.Event;
import com.example.demo.events.EventRepository;

@SpringBootApplication
public class SpringDemoRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoRestApiApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Autowired
	EventRepository eventRepositry;
	
//	@Bean
//	public ApplicationRunner applicationRunner() {
//		return (args) -> {
//			IntStream.range(1, 30).forEach(i -> generateEvent(i));
//		};
//	}
	
	private Event generateEvent(int index) {
		Event event = Event.builder()
				.name("event" + index)
				.description("test event")
				.build();
		
		return this.eventRepositry.save(event);
	}

}
