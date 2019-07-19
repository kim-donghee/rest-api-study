package com.example.demo.events;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class EventValidator {
	public void validate(EventDto eventDto, Errors errors) {
		
		if(eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
			errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong.");
			errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is wrong.");
			errors.reject("wrongPrices", "Values for price is wrong.");
		}
		
		LocalDateTime beginEnrollmentDateTime = eventDto.getBeginEnrollmentDateTime();
		LocalDateTime closeEnrollmentDateTime = eventDto.getCloseEnrollmentDateTime();
		LocalDateTime beginEventDateTime = eventDto.getBeginEventDateTime();
		LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
		
		if(endEventDateTime.isBefore(beginEventDateTime) || 
				endEventDateTime.isBefore(closeEnrollmentDateTime) ||
				endEventDateTime.isBefore(beginEnrollmentDateTime)) {
			errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong");
		}
		
		if(beginEventDateTime.isBefore(closeEnrollmentDateTime) || 
				beginEventDateTime.isBefore(beginEnrollmentDateTime)) {
			errors.rejectValue("beginEventDateTime", "wrongValue", "beginEventDateTime is wrong");
		}
		
		if(closeEnrollmentDateTime.isBefore(beginEnrollmentDateTime)) {
			errors.rejectValue("closeEnrollmentDateTime", "wrongValue", "closeEnrollmentDateTime is wrong");
		}
		
		
		// TODO beginEventDateTime
		// TODO closeEnrollmentDateTime
	}
}
