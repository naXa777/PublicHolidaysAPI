package com.avk.service;

import com.avk.common.ObjectNotFoundException;
import com.avk.database.CountryEntity;
import com.avk.database.ProvinceEntity;
import com.avk.database.PublicHolidayEntity;
import com.avk.model.CountryModel;
import com.avk.model.ProvinceModel;
import com.avk.model.PublicHolidayModel;
import com.avk.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CountryServiceTest {
    private final String invalidCountry = "QQ";
    private final String invalidProvince = "QQ";
    private final String newZealandName = "New Zealand";
    private final String australiaName = "Australia";
    private final String nswName = "New South Wales";
    private final String newYearsHolidayName = "New Year's Day";
    private final String newYearsHolidayName2 = "New Year's Day 2";
    private final String newYearsHolidayDay = "2017-01-01";
    private final String newYearsHolidayDay2 = "2017-01-02";
    private final String invalidDate = "2017A-01-01";
    private final String noHolidayDay = "2017-01-03";

    @Autowired
    private CountryService countryService;

    @MockBean
    private CountryRepository countryRepository;

    @BeforeEach
    public void setup() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        CountryEntity au = new CountryEntity();
        au.setId(CountryEntity.AUSTRALIA);
        au.setName(australiaName);

        CountryEntity nz = new CountryEntity();
        nz.setId(CountryEntity.NEW_ZEALAND);
        nz.setName(newZealandName);

        ProvinceEntity nsw = new ProvinceEntity();
        nsw.setId(1);
        nsw.setCountryEntity(au);
        nsw.setProvinceId(ProvinceEntity.NSW);
        nsw.setProvinceName(nswName);

        PublicHolidayEntity newYearsDay = new PublicHolidayEntity();
        newYearsDay.setId(1);
        newYearsDay.setName(newYearsHolidayName);
        newYearsDay.setHolidayDateValue(newYearsHolidayDay);
        newYearsDay.setHolidayDate(df.parse(newYearsHolidayDay));
        newYearsDay.setNationalHoliday(true);

        PublicHolidayEntity newYearsDay2 = new PublicHolidayEntity();
        newYearsDay2.setId(1);
        newYearsDay2.setName(newYearsHolidayName2);
        newYearsDay2.setHolidayDateValue(newYearsHolidayDay2);
        newYearsDay2.setHolidayDate(df.parse(newYearsHolidayDay2));
        newYearsDay2.setNationalHoliday(false);

        nsw.getPublicHolidayEntities().add(newYearsDay2);
        au.getProvinceEntities().add(nsw);

        List<CountryEntity> countryList = new ArrayList<>();
        countryList.add(au);
        countryList.add(nz);

        Set<PublicHolidayEntity> nationalHolidays = new HashSet<>();
        nationalHolidays.add(newYearsDay);

        given(this.countryRepository.findById(invalidCountry)).willReturn(Optional.empty());
        given(this.countryRepository.findById(CountryEntity.AUSTRALIA)).willReturn(Optional.of(au));
        given(this.countryRepository.findAll()).willReturn(countryList);
        given(this.countryRepository.getNationalPublicHolidays(CountryEntity.AUSTRALIA)).willReturn(nationalHolidays);
        given(this.countryRepository.getProvince(CountryEntity.AUSTRALIA, ProvinceEntity.NSW)).willReturn(nsw);
        given(this.countryRepository.getProvince(CountryEntity.AUSTRALIA, invalidProvince)).willReturn(null);
    }

    @Test
    public void getCountryNull() {
        assertThrows(IllegalArgumentException.class, () -> countryService.getCountry(null));
    }

    @Test
    public void getCountryNone() {
        assertThrows(ObjectNotFoundException.class, () -> countryService.getCountry(invalidCountry));
    }

    @Test
    public void getCountryAu() {
        CountryModel model = countryService.getCountry(CountryEntity.AUSTRALIA);

        assertEquals(CountryEntity.AUSTRALIA, model.getCountryId(), "There is a mismatch in the IDs");
        assertEquals(australiaName, model.getCountryName(), "There is a mismatch in the names");
    }

    @Test
    public void getCountries() {
        List<CountryModel> models = countryService.getCountries();

        assertEquals(2, models.size(), "Should only be 2 objects in the collection");
        assertEquals(CountryEntity.AUSTRALIA, models.get(0).getCountryId(), "There is a mismatch in the IDs");
        assertEquals(australiaName, models.get(0).getCountryName(), "There is a mismatch in the names");
        assertEquals(CountryEntity.NEW_ZEALAND, models.get(1).getCountryId(), "There is a mismatch in the IDs");
        assertEquals(newZealandName, models.get(1).getCountryName(), "There is a mismatch in the names");
    }

    @Test
    public void getCountryProvincesNullCountry() {
        assertThrows(IllegalArgumentException.class, () ->
                countryService.getCountryProvinces(null));
    }

    @Test
    public void getCountryProvincesInvalidCountry() {
        assertThrows(ObjectNotFoundException.class, () ->
                countryService.getCountryProvinces(invalidCountry));
    }

    @Test
    public void getCountryProvinces() {
        List<ProvinceModel> models = countryService.getCountryProvinces(CountryEntity.AUSTRALIA);

        assertEquals(1, models.size(), "Should only be 1 object in the collection");
        assertEquals(ProvinceEntity.NSW, models.get(0).getProvinceId(), "There is a mismatch in the IDs");
        assertEquals(nswName, models.get(0).getProvinceName(), "There is a mismatch in the names");
    }

    @Test
    public void getCountryHolidaysNullCountry() {
        assertThrows(IllegalArgumentException.class, () ->
                countryService.getPublicHolidays(null, null));
    }

    @Test
    public void getCountryHolidaysInvalidCountry() {
        assertThrows(ObjectNotFoundException.class, () ->
                countryService.getPublicHolidays(invalidCountry, null));
    }

    @Test
    public void getCountryHolidaysNational() {
        List<PublicHolidayModel> models = countryService.getPublicHolidays(CountryEntity.AUSTRALIA, null);

        assertEquals(1, models.size(), "Should only be 1 object in the collection");
        assertEquals(newYearsHolidayName, models.get(0).getName(), "There is a mismatch in the names");
        assertEquals(newYearsHolidayDay, models.get(0).getHolidayDate(), "There is a mismatch in the dates");
        assertEquals(true, models.get(0).getNationalHoliday(), "There is a mismatch in the national holiday value");
    }

    @Test
    public void getCountryHolidaysInvalidProvince() {
        assertThrows(ObjectNotFoundException.class, () ->
                countryService.getPublicHolidays(CountryEntity.AUSTRALIA, invalidProvince));
    }

    @Test
    public void getCountryHolidaysProvince() {
        List<PublicHolidayModel> models = countryService.getPublicHolidays(CountryEntity.AUSTRALIA, ProvinceEntity.NSW);

        assertEquals(2, models.size(), "Should only be 2 objects in the collection");
        assertEquals(newYearsHolidayName, models.get(0).getName(), "There is a mismatch in the names");
        assertEquals(newYearsHolidayDay, models.get(0).getHolidayDate(), "There is a mismatch in the dates");
        assertEquals(true, models.get(0).getNationalHoliday(), "There is a mismatch in the national holiday value");
        assertEquals(newYearsHolidayName2, models.get(1).getName(), "There is a mismatch in the names");
        assertEquals(newYearsHolidayDay2, models.get(1).getHolidayDate(), "There is a mismatch in the dates");
        assertEquals(false, models.get(1).getNationalHoliday(), "There is a mismatch in the national holiday value");
    }

    @Test
    public void getHolidayNullCountry() {
        assertThrows(IllegalArgumentException.class, () ->
                countryService.getPublicHoliday(null, newYearsHolidayDay, null));
    }

    @Test
    public void getHolidayInvalidCountry() {
        assertThrows(ObjectNotFoundException.class, () ->
                countryService.getPublicHoliday(invalidCountry, newYearsHolidayDay, null));
    }

    @Test
    public void getHolidayInvalidProvince() {
        assertThrows(ObjectNotFoundException.class, () ->
                countryService.getPublicHoliday(CountryEntity.AUSTRALIA, newYearsHolidayDay, invalidProvince));
    }

    @Test
    public void getHoliday() {
        PublicHolidayModel model = countryService.getPublicHoliday(CountryEntity.AUSTRALIA, newYearsHolidayDay, null);

        assertEquals(newYearsHolidayName, model.getName(), "There is a mismatch in the names");
        assertEquals(newYearsHolidayDay, model.getHolidayDate(), "There is a mismatch in the dates");
    }

    @Test
    public void getHolidayProvince() {
        PublicHolidayModel model = countryService.getPublicHoliday(CountryEntity.AUSTRALIA, newYearsHolidayDay2, ProvinceEntity.NSW);

        assertEquals(newYearsHolidayName2, model.getName(), "There is a mismatch in the names");
        assertEquals(newYearsHolidayDay2, model.getHolidayDate(), "There is a mismatch in the dates");
    }

    @Test
    public void getHolidayNone() {
        PublicHolidayModel model = countryService.getPublicHoliday(CountryEntity.AUSTRALIA, noHolidayDay, null);

        assertNull(model);
    }
}
