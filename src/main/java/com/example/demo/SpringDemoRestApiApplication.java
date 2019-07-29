package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.events.Event;
import com.example.demo.events.EventRepository;

@SpringBootApplication
public class SpringDemoRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoRestApiApplication.class, args);
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
