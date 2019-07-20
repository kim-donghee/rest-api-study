package com.example.demo.event;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.demo.events.Event;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class EventTest {
	
	@Test
	public void builder() {
		Event event = Event.builder()
				.name("Inflearn Spring REST API")
				.description("REST API delvelopment with Spring")
				.build();
		assertThat(event).isNotNull();
	}
	
	@Test
	public void javaBean() {
		// Given
		String name = "Event";
		String description = "Spring";
		
		// When
		Event event = new Event();
		event.setName(name);
		event.setDescription(description);
		
		// Then
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(description);
		
	}
	
	@Test @Ignore
	public void testFree() {
		// Given
		Event event = Event.builder()
				.basePrice(0)
				.maxPrice(0)
				.build();
		
		// When
		event.update();		
		
		// Then
		assertThat(event.isFree()).isTrue();
		
		// Given
		event = Event.builder()
				.basePrice(100)
				.maxPrice(0)
				.build();
		
		// When
		event.update();		
		
		// Then
		assertThat(event.isFree()).isFalse();
		
		// Given
		event = Event.builder()
				.basePrice(0)
				.maxPrice(100)
				.build();
		
		// When
		event.update();		
		
		// Then
		assertThat(event.isFree()).isFalse();
	}
	
	@Test
	@Parameters
	public void testFree2(int basePrice, int maxPrice, boolean isFree) {
		// Given
		Event event = Event.builder()
				.basePrice(basePrice)
				.maxPrice(maxPrice)
				.build();
		
		// When
		event.update();		
		
		// Then
		assertThat(event.isFree()).isEqualTo(isFree);
	}
	
	private Object[] parametersForTestFree2() {
		return new Object[] {
				new Object[] { 0, 0, true },
				new Object[] { 100, 0, false },
				new Object[] { 0, 100, false }
		};
	}
	
	@Test @Ignore
	public void testOffline() {
		// Given
		Event event = Event.builder()
				.location("강남역 네이버 D2 스타텁 팩토리")
				.build();
		
		// When
		event.update();		
		
		// Then
		assertThat(event.isOffline()).isTrue();
		
		// Given
		event = Event.builder()
				.build();
		
		// When
		event.update();		
		
		// Then
		assertThat(event.isOffline()).isFalse();
	}
	
	@Test
	@Parameters
	public void testOffline2(String location, boolean isOffline) {
		// Given
		Event event = Event.builder()
				.location(location)
				.build();
		
		// When
		event.update();		
		
		// Then
		assertThat(event.isOffline()).isEqualTo(isOffline);
	}
	
	private Object[] parametersForTestOffline2() {
		return new Object[] {
				new Object[] { "강남역 네이버 D2 스타텁 팩토리", true },
				new Object[] { null, false },
				new Object[] { "         ", false }
		};
	}

}
