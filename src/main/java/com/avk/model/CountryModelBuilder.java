package com.avk.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.avk.controller.CountryController;

public class CountryModelBuilder
{
	public static CountryModel buildModel(String id, String name)
	{
		CountryModel model = new CountryModel();
		
		model.setCountryId(id);
		model.setCountryName(name);
		
		return model;
	}
	
	public static void buildLinks(CountryModel model)
	{
		model.add(linkTo(CountryController.class).slash(model.getCountryId()).withSelfRel());
		model.add(linkTo(CountryController.class).slash(model.getCountryId()).slash("provinces").withRel("provinces"));
		model.add(linkTo(methodOn(CountryController.class).getCountryHolidays(model.getCountryId(), null)).withRel("holidays"));
	}
}
