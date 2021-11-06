package com.avk.controller;

import com.avk.common.ObjectNotFoundException;
import com.avk.database.CountryEntity;
import com.avk.database.ProvinceEntity;
import com.avk.model.*;
import com.avk.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebAppConfiguration
@WebMvcTest(CountryController.class)
public class CountryControllerTest {
    private final String invalidCountry = "QQ";
    private final String newZealandName = "New Zealand";
    private final String australiaName = "Australia";
    private final String nswName = "New South Wales";
    private final String basePath = "http://localhost/";
    private final String controllerPath = "countries/";
    private final String newYearsHolidayName = "New Year's Day";
    private final String newYearsHolidayDay = "2017-01-01";
    private final String noHolidayDay = "2017-01-03";
    private final String invalidDate = "2017A-01-01";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @BeforeEach
    public void setup() {
        CountryModel aus = CountryModelBuilder.buildModel(CountryEntity.AUSTRALIA, australiaName);
        CountryModel nz = CountryModelBuilder.buildModel(CountryEntity.NEW_ZEALAND, newZealandName);

        ProvinceModel nsw = ProvinceModelBuilder.buildModel(ProvinceEntity.NSW, nswName);

        PublicHolidayModel newYearsDay = PublicHolidayModelBuilder.buildModel(newYearsHolidayName, newYearsHolidayDay, true);

        List<CountryModel> countryList = new ArrayList<>();
        countryList.add(aus);
        countryList.add(nz);

        List<ProvinceModel> provinceList = new ArrayList<>();
        provinceList.add(nsw);

        List<PublicHolidayModel> publicHolidayList = new ArrayList<>();
        publicHolidayList.add(newYearsDay);

        given(this.countryService.getCountries()).willReturn(countryList);
        given(this.countryService.getCountry(CountryEntity.NEW_ZEALAND)).willReturn(nz);
        given(this.countryService.getCountry(invalidCountry)).willThrow(new ObjectNotFoundException(""));
        given(this.countryService.getCountryProvinces(CountryEntity.AUSTRALIA)).willReturn(provinceList);
        given(this.countryService.getCountryProvinces(invalidCountry)).willThrow(new ObjectNotFoundException(""));
        given(this.countryService.getPublicHolidays(CountryEntity.AUSTRALIA, null)).willReturn(publicHolidayList);
        given(this.countryService.getPublicHolidays(invalidCountry, null)).willThrow(new ObjectNotFoundException(""));
        given(this.countryService.getPublicHoliday(CountryEntity.AUSTRALIA, newYearsHolidayDay, null)).willReturn(newYearsDay);
        given(this.countryService.getPublicHoliday(CountryEntity.AUSTRALIA, noHolidayDay, null)).willReturn(null);
        given(this.countryService.getPublicHoliday(CountryEntity.AUSTRALIA, invalidDate, null)).willThrow(new IllegalArgumentException());
        given(this.countryService.getPublicHoliday(invalidCountry, newYearsHolidayDay, null)).willThrow(new ObjectNotFoundException(""));
    }

    @Test
    public void testGetCountry() throws Exception {
        ResultActions actions = mockMvc.perform(get("/countries/" + CountryEntity.NEW_ZEALAND).accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        actions.andExpect(jsonPath("$.countryId", is(CountryEntity.NEW_ZEALAND)));
        actions.andExpect(jsonPath("$.countryName", is(newZealandName)));
        actions.andExpect(jsonPath("$._links.self.href", is(basePath + controllerPath + CountryEntity.NEW_ZEALAND)));
        actions.andExpect(jsonPath("$._links.provinces.href", is(basePath + controllerPath + CountryEntity.NEW_ZEALAND + "/provinces")));
        actions.andExpect(jsonPath("$._links.holidays.href", is(basePath + controllerPath + CountryEntity.NEW_ZEALAND + "/holidays{?province_id}")));
    }

    @Test
    public void testGetCountryHolidays() throws Exception {
        ResultActions actions = mockMvc.perform(get("/countries/" + CountryEntity.AUSTRALIA + "/holidays").accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        actions.andExpect(jsonPath("$", hasSize(1)));
        actions.andExpect(jsonPath("$[0].name", is(newYearsHolidayName)));
        actions.andExpect(jsonPath("$[0].holidayDate", is(newYearsHolidayDay)));
    }

    @Test
    public void testGetCountryNotFound() throws Exception {
        ResultActions actions = mockMvc.perform(get("/countries/" + invalidCountry).accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNotFound());
    }

    @Test
    public void testGetCountries() throws Exception {
        ResultActions actions = mockMvc.perform(get("/countries").accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        actions.andExpect(jsonPath("$", hasSize(2)));
        actions.andExpect(jsonPath("$[0].countryId", is(CountryEntity.AUSTRALIA)));
        actions.andExpect(jsonPath("$[1].countryId", is(CountryEntity.NEW_ZEALAND)));
    }

    @Test
    public void testGetProvinces() throws Exception {
        ResultActions actions = mockMvc.perform(get("/countries/" + CountryEntity.AUSTRALIA + "/provinces").accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        actions.andExpect(jsonPath("$", hasSize(1)));
        actions.andExpect(jsonPath("$[0].provinceId", is(ProvinceEntity.NSW)));
    }

    @Test
    public void testGetProvincesInvalidCountry() throws Exception {
        ResultActions actions = mockMvc.perform(get("/countries/" + invalidCountry + "/provinces").accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNotFound());
    }

    @Test
    public void checkPublicHoliday() throws Exception {
        ResultActions actions = mockMvc.perform(get("/countries/" + CountryEntity.AUSTRALIA + "/" + newYearsHolidayDay).accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        actions.andExpect(jsonPath("$.isHoliday", is(true)));
        actions.andExpect(jsonPath("$.publicHoliday.holidayDate", is(newYearsHolidayDay)));
    }

    @Test
    public void checkPublicHolidayNoHoliday() throws Exception {
        ResultActions actions = mockMvc.perform(get("/countries/" + CountryEntity.AUSTRALIA + "/" + noHolidayDay).accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());
        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        actions.andExpect(jsonPath("$.isHoliday", is(false)));
    }

    @Test
    public void checkPublicHolidayInvalidDate() throws Exception {
        ResultActions actions = mockMvc.perform(get("/countries/" + CountryEntity.AUSTRALIA + "/" + invalidDate).accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isBadRequest());
    }

    @Test
    public void checkPublicHolidayInvalidCountry() throws Exception {
        ResultActions actions = mockMvc.perform(get("/countries/" + invalidCountry + "/" + newYearsHolidayDay).accept(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNotFound());
    }
}
