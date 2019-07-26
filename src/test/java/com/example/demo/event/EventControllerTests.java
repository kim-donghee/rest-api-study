package com.example.demo.event;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.example.demo.common.BaseControllerTest;
import com.example.demo.common.TestDescription;
import com.example.demo.events.Event;
import com.example.demo.events.EventDto;
import com.example.demo.events.EventRepository;

public class EventControllerTests extends BaseControllerTest {
	
	@Autowired
	EventRepository eventRepositry;
	
//	@MockBean
//	EventRepository eventRepository;
	
	@Test @Ignore
	@TestDescription("정상적으로 이벤트를 생성하는 테스트")
	public void createEvent() throws Exception {
		Event event = Event.builder()
				.id(100)
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11 , 23, 14, 21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11 , 24, 14, 21))
				.beginEventDateTime(LocalDateTime.of(2018, 11 , 25, 14, 21))
				.endEventDateTime(LocalDateTime.of(2018, 11 , 26, 14, 21))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("강남역 D2 스타텁 팩토리")
				.free(true)
				.offline(false)
				.build();
//		event.setId(10);
//		Mockito.when(eventRepository.save(event)).thenReturn(event);
		
		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(event)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("id").value(Matchers.not(100)))
			.andExpect(jsonPath("free").value(Matchers.not(true)))
			.andExpect(header().exists(HttpHeaders.LOCATION))
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE));
	}
	
	@Test  @Ignore
	@TestDescription("정상적으로 이벤트를 생성하는 테스트")
	public void createEvent2() throws Exception {
		EventDto event = EventDto.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11 , 23, 14, 21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11 , 24, 14, 21))
				.beginEventDateTime(LocalDateTime.of(2018, 11 , 25, 14, 21))
				.endEventDateTime(LocalDateTime.of(2018, 11 , 26, 14, 21))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("강남역 D2 스타텁 팩토리")
				.build();
		
		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(event)))
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("free").value(false))
			.andExpect(jsonPath("offline").value(true))
			.andExpect(header().exists(HttpHeaders.LOCATION))
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.query-events").exists())
			.andExpect(jsonPath("_links.update-event").exists())
			.andDo(document("create-event", 
					links(
							linkWithRel("self").description("link to self"),
							linkWithRel("query-events").description("link to query events"),
							linkWithRel("update-event").description("link to update an existring"),
							linkWithRel("profile").description("link to profile")
							)
					,
					requestHeaders(
							headerWithName(HttpHeaders.ACCEPT).description("accept header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
							)					
					,
					requestFields(
							fieldWithPath("name").description("Name of new event"),
							fieldWithPath("description").description("description of new event"),
							fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime of new event"),
							fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime of new event"),
							fieldWithPath("beginEventDateTime").description("beginEventDateTime of new event"),
							fieldWithPath("endEventDateTime").description("endEventDateTime of new event"),
							fieldWithPath("location").description("location of new event"),
							fieldWithPath("basePrice").description("basePrice of new event"),
							fieldWithPath("maxPrice").description("maxPrice of new event"),
							fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event")
							)
					,
					responseHeaders(
							headerWithName(HttpHeaders.LOCATION).description("location header"),
							headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
							)
					,
					responseFields( // relaxedResponseFields
							fieldWithPath("id").description("id of new event"),
							fieldWithPath("name").description("Name of new event"),
							fieldWithPath("description").description("description of new event"),
							fieldWithPath("beginEnrollmentDateTime").description("beginEnrollmentDateTime of new event"),
							fieldWithPath("closeEnrollmentDateTime").description("closeEnrollmentDateTime of new event"),
							fieldWithPath("beginEventDateTime").description("beginEventDateTime of new event"),
							fieldWithPath("endEventDateTime").description("endEventDateTime of new event"),
							fieldWithPath("location").description("location of new event"),
							fieldWithPath("basePrice").description("basePrice of new event"),
							fieldWithPath("maxPrice").description("maxPrice of new event"),
							fieldWithPath("limitOfEnrollment").description("limitOfEnrollment of new event"),
							fieldWithPath("offline").description("offline of new event"),
							fieldWithPath("free").description("free of new event"),
							fieldWithPath("eventStatus").description("eventStatus of new event"),
							fieldWithPath("_links.self.href").description("link to self"),
							fieldWithPath("_links.query-events.href").description("link to query-events"),
							fieldWithPath("_links.update-event.href").description("link to update-event"),
							fieldWithPath("_links.profile.href").description("link to profile")
							)
					)
				)
			;
	}
	
	@Test @Ignore
	@TestDescription("입력 받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
	public void createEventBadRequest() throws Exception {
		Event event = Event.builder()
				.id(100)
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11 , 23, 14, 21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11 , 24, 14, 21))
				.beginEventDateTime(LocalDateTime.of(2018, 11 , 25, 14, 21))
				.endEventDateTime(LocalDateTime.of(2018, 11 , 26, 14, 21))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("강남역 D2 스타텁 팩토리")
				.free(true)
				.offline(false)
				.build();
		
		mockMvc.perform(post("/api/events/")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON)
				.content(objectMapper.writeValueAsString(event)))
			.andDo(print())
			.andExpect(status().isBadRequest());
	}
	
	@Test @Ignore
	@TestDescription("입력 값 비어있는 경우에 에러가 발생하는 테스트")
	public void createEventBadRequestEmptyInput() throws Exception {
		EventDto eventDto = EventDto.builder().build();
		
		this.mockMvc.perform(post("/api/events")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaTypes.HAL_JSON)
					.content(objectMapper.writeValueAsString(eventDto)))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test @Ignore
	@TestDescription("입력 값이 잘못된 경우에 에러가 발생하는 테스트")
	public void createEventBadRequestWrongInput() throws Exception {
		EventDto eventDto = EventDto.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11 , 26, 14, 21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11 , 25, 14, 21))
				.beginEventDateTime(LocalDateTime.of(2018, 11 , 24, 14, 21))
				.endEventDateTime(LocalDateTime.of(2018, 11 , 23, 14, 21))
				.basePrice(10000)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("강남역 D2 스타텁 팩토리")
				.build();
		
		this.mockMvc.perform(post("/api/events")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.accept(MediaTypes.HAL_JSON)
					.content(objectMapper.writeValueAsString(eventDto)))
				.andExpect(status().isBadRequest())
				.andDo(print())
				.andExpect(jsonPath("content[0].objectName").exists())
//				.andExpect(jsonPath("$[0].field").exists())
				.andExpect(jsonPath("content[0].defaultMessage").exists())
				.andExpect(jsonPath("content[0].code").exists())
//				.andExpect(jsonPath("$[0].rejectedValue").exists());
				.andExpect(jsonPath("_links.index").exists());
				;
		
	}
	
	@Test @Ignore
	@TestDescription("30개의 이벤트를 10개씩 두 전째 페이지 조회하기")
	public void queryEvents() throws Exception {
		// Given
		IntStream.range(0, 30).forEach(i -> {
			this.generateEvent(i);
		});
		
		// When & Then
		this.mockMvc.perform(get("/api/events")
				.param("page", "1")
				.param("size", "10")
				.param("sort", "name,DESC"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("page").exists())
			.andExpect(jsonPath("_embedded.eventList[0]._links.self").exists())
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.profile").exists())
			.andDo(document("query-events"))
		;
	}
	
	private Event generateEvent(int index) {
		Event event = Event.builder()
				.name("Spring")
				.description("REST API Development with Spring")
				.beginEnrollmentDateTime(LocalDateTime.of(2018, 11 , 23, 14, 21))
				.closeEnrollmentDateTime(LocalDateTime.of(2018, 11 , 24, 14, 21))
				.beginEventDateTime(LocalDateTime.of(2018, 11 , 25, 14, 21))
				.endEventDateTime(LocalDateTime.of(2018, 11 , 26, 14, 21))
				.basePrice(100)
				.maxPrice(200)
				.limitOfEnrollment(100)
				.location("강남역 D2 스타텁 팩토리")
				.build();
		
		event.update();
		return this.eventRepositry.save(event);
	}
	
	@Test @Ignore
	@TestDescription("기존의 이벤트를 하나 조회하기")
	public void getEvent() throws Exception {
		// Given
		Event event = this.generateEvent(100);
		
		// When & Then
		this.mockMvc.perform(get("/api/events/{id}", event.getId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").exists())
			.andExpect(jsonPath("id").exists())
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.profile").exists())
			.andDo(document("get-an-event"))
		;
	}
	
	@Test @Ignore
	@TestDescription("없는 이벤트 조회했을 때 404 응답받기")
	public void getEvent404() throws Exception {
		// When & Then
		this.mockMvc.perform(get("/api/events/{id}", 100))
			.andExpect(status().isNotFound())
		;
	}
	
	@Test
	@TestDescription("정상적으로 수정이 성공하는 테스트")
	public void updateEvent() throws Exception {
		// Given
		Event event = this.generateEvent(100);
		EventDto eventDto = this.modelMapper.map(event, EventDto.class);
		String eventName = "Updated Event";
		eventDto.setName(eventName);
		
		// When & Then
		this.mockMvc.perform(put("/api/events/{id}", event.getId())
				.accept(MediaTypes.HAL_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(eventDto)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("name").value(eventName))
			.andExpect(jsonPath("_links.self").exists())
			.andDo(document("update-event"))
		;
	}
	
	@Test
	@TestDescription("입력값이 잘못된 경우에 이벤트 수정 실패")
	public void updateEvent400Wrong() throws Exception {
		// Given
		Event event = this.generateEvent(100);
		EventDto eventDto = this.modelMapper.map(event, EventDto.class);
		eventDto.setBasePrice(20000);
		eventDto.setMaxPrice(1000);
		
		// When & Then
		this.mockMvc.perform(put("/api/events/{id}", event.getId())
				.accept(MediaTypes.HAL_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(eventDto)))
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}
	
	@Test
	@TestDescription("입력값이 비어있는 경우에 이벤트 수정 실패")
	public void updateEvent400Empty() throws Exception {
		// Given
		Event event = this.generateEvent(100);
		EventDto eventDto = EventDto.builder().build();
		
		// When & Then
		this.mockMvc.perform(put("/api/events/{id}", event.getId())
				.accept(MediaTypes.HAL_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(eventDto)))
			.andDo(print())
			.andExpect(status().isBadRequest())
		;
	}
	
	@Test
	@TestDescription("존재하지 않는 이벤트 수정 실패")
	public void updateEvent404() throws Exception {
		// Given
		Event event = this.generateEvent(100);
		EventDto eventDto = this.modelMapper.map(event, EventDto.class);
		
		// When & Then
		this.mockMvc.perform(put("/api/events/123123")
				.accept(MediaTypes.HAL_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(eventDto)))
			.andDo(print())
			.andExpect(status().isNotFound())
		;
	}

}
