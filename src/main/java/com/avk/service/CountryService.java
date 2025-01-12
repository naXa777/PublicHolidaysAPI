package com.avk.service;

import com.avk.model.CountryModel;
import com.avk.model.ProvinceModel;
import com.avk.model.PublicHolidayModel;

import java.util.List;

public interface CountryService {

    String DATE_FORMAT_PATTERN = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

    CountryModel getCountry(String id);

    List<ProvinceModel> getCountryProvinces(String id);

    List<CountryModel> getCountries();

    List<PublicHolidayModel> getPublicHolidays(String countryId, String provinceId);

    PublicHolidayModel getPublicHoliday(String id, String date, String provinceId);

    List<String> getBusinessDaysIn(String id, String date, Integer days, String provinceId);
}
