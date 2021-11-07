package com.avk.service;

import com.avk.model.CountryModel;
import com.avk.model.ProvinceModel;
import com.avk.model.PublicHolidayModel;

import java.util.List;

public interface CountryService {
    CountryModel getCountry(String id);

    List<ProvinceModel> getCountryProvinces(String id);

    List<CountryModel> getCountries();

    List<PublicHolidayModel> getPublicHolidays(String countryId, String provinceId);

    PublicHolidayModel getPublicHoliday(String id, String date, String provinceId);

    String getBusinessDaysIn(String id, String date, Integer days, String provinceId);
}
