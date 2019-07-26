package com.example.demo.common;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.validation.Errors;

import com.example.demo.index.IndexController;

public class ErrorsResource extends Resource<Errors> {
	
	public ErrorsResource(Errors errors, Link... links) {
		super(errors, links);
		add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
	}
	
}
