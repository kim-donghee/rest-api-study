package com.example.demo.events;

import java.time.LocalDateTime;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class EventDto {
	
	@NotEmpty
	private String name;
	@NotEmpty
	private String description;
	@NotNull
	private LocalDateTime beginEnrollmentDateTime;	// 등록 시작일시
	@NotNull
	private LocalDateTime closeEnrollmentDateTime;	// 등록 종료일시
	@NotNull
	private LocalDateTime beginEventDateTime;		// 이벤트 시작일시
	@NotNull
	private LocalDateTime endEventDateTime;			// 이벤트 종료일시
	@NotEmpty
	private String location;	// (optional) 이게 없으면 온라인 모임
	@Min(0)
	private int basePrice;		// (optional)
	@Min(0)
	private int maxPrice;		// (optional)
	@Min(0)
	private int limitOfEnrollment;
	
}
