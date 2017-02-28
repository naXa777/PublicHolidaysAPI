package com.avk.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avk.model.CountryModel;
import com.avk.model.CountryModelBuilder;
import com.avk.model.DateModel;
import com.avk.model.ProvinceModel;
import com.avk.model.PublicHolidayModel;
import com.avk.service.CountryService;

@RestController
@RequestMapping("/countries")
public class CountryController
{
	private CountryService countryService;
	
	public CountryController(CountryService countryService)
	{
		this.countryService = countryService;
	}
	
	@GetMapping
	public List<CountryModel> getCountries() throws RuntimeException
	{
		List<CountryModel> countryModels = countryService.getCountries();
		
		countryModels.forEach(model -> CountryModelBuilder.buildLinks(model));
		
		return countryModels;
	}
	
	@GetMapping("/{id}")
	public CountryModel getCountry(@PathVariable String id) throws RuntimeException
	{
		CountryModel model = countryService.getCountry(id);
		
		CountryModelBuilder.buildLinks(model);
		
		return model;
	}
	
	@GetMapping("/{id}/provinces")
	public List<ProvinceModel> getCountryProvinces(@PathVariable String id) throws RuntimeException
	{
		return countryService.getCountryProvinces(id);
	}
	
	@GetMapping("/{id}/{date}")
	public DateModel checkPublicHolidayDate(@PathVariable String id, @PathVariable String date, @RequestParam(required = false, name = "province_id") String provinceId) throws RuntimeException
	{
		DateModel dateModel = new DateModel();
		
		PublicHolidayModel publicHoliday = countryService.getPublicHoliday(id, date, provinceId);
		
		dateModel.setIsHoliday(publicHoliday != null);
		dateModel.setPublicHoliday(publicHoliday);
		
		return dateModel;
	}
	
	@GetMapping("/{id}/holidays")
	public List<PublicHolidayModel> getCountryHolidays(@PathVariable String id,
			@RequestParam(required = false, name = "province_id") String provinceId) throws RuntimeException
	{
		return countryService.getPublicHolidays(id, provinceId);
	}
}
