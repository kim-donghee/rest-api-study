package com.example.demo.events;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class EventDto {
	
	private String name;
	private String description;
	private LocalDateTime beginEnrollmentDateTime;	// 등록 시작일시
	private LocalDateTime closeEnrollmentDateTime;	// 등록 종료일시
	private LocalDateTime beginEventDateTime;		// 이벤트 시작일시
	private LocalDateTime endEventDateTime;			// 이벤트 종료일시
	private String location;	// (optional) 이게 없으면 온라인 모임
	private int basePrice;		// (optional)
	private int maxPrice;		// (optional)
	private int limitOfEnrollment;
	
}
