package com.avk.service;

import java.util.List;

import com.avk.model.CountryModel;
import com.avk.model.ProvinceModel;
import com.avk.model.PublicHolidayModel;

public interface CountryService
{
	public CountryModel getCountry(String id);
	public List<ProvinceModel> getCountryProvinces(String id);
	public List<CountryModel> getCountries();
	public List<PublicHolidayModel> getPublicHolidays(String countryId, String provinceId);
	public PublicHolidayModel getPublicHoliday(String id, String date, String provinceId);
}
