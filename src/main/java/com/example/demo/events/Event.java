package com.example.demo.events;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode(of = { "id" })
@Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Event {
	
	@Id @GeneratedValue
	private Integer id;
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
	private boolean offline;
    private boolean free;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

}
