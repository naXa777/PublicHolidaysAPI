package com.avk.controller;

import com.avk.model.*;
import com.avk.service.CountryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

@Validated
@RestController
@RequestMapping("/countries")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<CountryModel> getCountries() throws RuntimeException {
        List<CountryModel> countryModels = countryService.getCountries();

        countryModels.forEach(CountryModelBuilder::buildLinks);

        return countryModels;
    }

    @GetMapping("/{id}")
    public CountryModel getCountry(@PathVariable String id) throws RuntimeException {
        CountryModel model = countryService.getCountry(id);

        CountryModelBuilder.buildLinks(model);

        return model;
    }

    @GetMapping("/{id}/provinces")
    public List<ProvinceModel> getCountryProvinces(@PathVariable String id) throws RuntimeException {
        return countryService.getCountryProvinces(id);
    }

    @GetMapping("/{id}/{date}")
    public DateModel checkPublicHolidayDate(
            @PathVariable String id,
            @Pattern(regexp = CountryService.DATE_FORMAT_PATTERN, message = "Date must be in the format YYYY-MM-DD")
            @PathVariable String date,
            @RequestParam(required = false, name = "province_id") String provinceId
    ) throws RuntimeException {
        DateModel dateModel = new DateModel();

        PublicHolidayModel publicHoliday = countryService.getPublicHoliday(id, date, provinceId);

        dateModel.setIsHoliday(publicHoliday != null);
        dateModel.setPublicHoliday(publicHoliday);

        return dateModel;
    }

    @GetMapping("/{id}/holidays")
    public List<PublicHolidayModel> getCountryHolidays(
            @PathVariable String id,
            @RequestParam(required = false, name = "province_id") String provinceId
    ) throws RuntimeException {
        return countryService.getPublicHolidays(id, provinceId);
    }

    @GetMapping("/{id}/{date}/{days}")
    public List<String> getBusinessDaysIn(
            @PathVariable String id,
            @Pattern(regexp = CountryService.DATE_FORMAT_PATTERN, message = "Date must be in the format YYYY-MM-DD")
            @PathVariable String date,
            @PathVariable Integer days,
            @RequestParam(required = false, name = "province_id") String provinceId
    ) throws RuntimeException {
        return countryService.getBusinessDaysIn(id, date, days, provinceId);
    }
}
