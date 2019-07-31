package com.example.demo.events;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.accounts.Account;
import com.example.demo.accounts.CurrentUser;
import com.example.demo.common.ErrorsResource;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {
	
	private final EventRepository eventRepository;
	
	private final ModelMapper modelMapper;
	
	private final EventValidator eventValidator;
	
	public EventController(EventRepository eventRepository, ModelMapper modelMapper, 
			EventValidator eventValidator) {
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
		this.eventValidator = eventValidator;
	}
	
	@PostMapping
	public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto, Errors errors,
			@CurrentUser Account currentUser) {
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		eventValidator.validate(eventDto, errors);
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
//		Event event = Event.builder().build();
		
		Event event = modelMapper.map(eventDto, Event.class);
		event.update();
		event.setManager(currentUser);
		
		Event savedEvent = eventRepository.save(event);		
		ControllerLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(savedEvent.getId());
		URI createdUri = selfLinkBuilder.toUri();
		EventResource eventResource = new EventResource(savedEvent);
		
		eventResource.add(linkTo(EventController.class).withRel("query-events"));
//		eventResource.add(selfLinkBuilder.withSelfRel());
		eventResource.add(selfLinkBuilder.withRel("update-event"));
		eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile"));
		
		return ResponseEntity.created(createdUri).body(eventResource);
	}
	
	// return ResponseEntity.badRequest().body(errors); 자바빈 스펙을 준수하지못해 변환시 에러 발생
	
	private ResponseEntity badRequest(Errors errors) {
		return ResponseEntity.badRequest().body(new ErrorsResource(errors));
	}
	
	@GetMapping
	public ResponseEntity queryEvent(Pageable pageable, 
			PagedResourcesAssembler<Event> assembler, 
//			@AuthenticationPrincipal AccountAdapter currentUser
//			@AuthenticationPrincipal(expression = "account") Account account	
			@CurrentUser Account account
			) {
		Page<Event> page = this.eventRepository.findAll(pageable);
		var pageResoureces = assembler.toResource(page, e -> new EventResource(e));
		pageResoureces.add(new Link("/docs/index.html#resources-events-list").withRel("profile"));
//		if(currentUser != null) {
//			pageResoureces.add(linkTo(EventController.class).withRel("create-event"));
//		}
		if(account != null) {
			pageResoureces.add(linkTo(EventController.class).withRel("create-event"));
		}
		return ResponseEntity.ok(pageResoureces);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity queryEvent(@PathVariable Integer id,
			@CurrentUser Account currentUser) {
		Optional<Event> optionalEvent = this.eventRepository.findById(id);
		if(optionalEvent.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Event event = optionalEvent.get();
		EventResource eventResource = new EventResource(event);
		eventResource.add(new Link("/docs/index.html#resources-events-get").withRel("profile"));
		if(event.getManager().equals(currentUser)) {
			eventResource.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
		}
		
		return ResponseEntity.ok().body(eventResource);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity updateEvent(
			@PathVariable Integer id,
			@RequestBody @Valid EventDto eventDto, Errors errors,
			@CurrentUser Account currentUser) {
		Optional<Event> optionalEvent = this.eventRepository.findById(id);
		if(optionalEvent.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		eventValidator.validate(eventDto, errors);
		if(errors.hasErrors()) {
			return badRequest(errors);
		}
		
		Event exisingEvent = optionalEvent.get();
		if(!exisingEvent.getManager().equals(currentUser)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		modelMapper.map(eventDto, exisingEvent);
		exisingEvent.update();
		Event updatedEvent = this.eventRepository.save(exisingEvent);
		
		EventResource eventResource = new EventResource(updatedEvent);
		eventResource.add(new Link("/docs/index.html#resources-events-update").withRel("profile"));
		
		return ResponseEntity.ok(eventResource);
	}
	
}
