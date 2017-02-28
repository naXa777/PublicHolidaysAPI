package com.avk.service;

import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.avk.common.ObjectNotFoundException;
import com.avk.database.CountryEntity;
import com.avk.database.ProvinceEntity;
import com.avk.database.PublicHolidayEntity;
import com.avk.model.CountryModel;
import com.avk.model.ProvinceModel;
import com.avk.model.PublicHolidayModel;
import com.avk.repository.CountryRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryServiceTest
{
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
	
    @Before
    public void setup() throws Exception
    {
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
    	
    	given(this.countryRepository.findOne(invalidCountry)).willReturn(null);
    	given(this.countryRepository.findOne(CountryEntity.AUSTRALIA)).willReturn(au);
    	given(this.countryRepository.findAll()).willReturn(countryList);
    	given(this.countryRepository.getNationalPublicHolidays(CountryEntity.AUSTRALIA)).willReturn(nationalHolidays);
    	given(this.countryRepository.getProvince(CountryEntity.AUSTRALIA, ProvinceEntity.NSW)).willReturn(nsw);
    	given(this.countryRepository.getProvince(CountryEntity.AUSTRALIA, invalidProvince)).willReturn(null);
    }
	
	@Test(expected = IllegalArgumentException.class)
	public void getCountryNull()
	{
		countryService.getCountry(null);
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void getCountryNone()
	{
		countryService.getCountry(invalidCountry);
	}
	
	@Test
	public void getCountryAu()
	{
		CountryModel model = countryService.getCountry(CountryEntity.AUSTRALIA);
		
		assertEquals("There is a mismatch in the IDs", CountryEntity.AUSTRALIA, model.getCountryId());
		assertEquals("There is a mismatch in the names", australiaName, model.getCountryName());
	}
	
	@Test
	public void getCountries()
	{
		List<CountryModel> models = countryService.getCountries();
		
		assertEquals("Should only be 2 objects in the collection", 2, models.size());
		assertEquals("There is a mismatch in the IDs", CountryEntity.AUSTRALIA, models.get(0).getCountryId());
		assertEquals("There is a mismatch in the names", australiaName, models.get(0).getCountryName());
		assertEquals("There is a mismatch in the IDs", CountryEntity.NEW_ZEALAND, models.get(1).getCountryId());
		assertEquals("There is a mismatch in the names", newZealandName, models.get(1).getCountryName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getCountryProvincesNullCountry()
	{
		countryService.getCountryProvinces(null);
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void getCountryProvincesInvalidCountry()
	{
		countryService.getCountryProvinces(invalidCountry);
	}
	
	@Test
	public void getCountryProvinces()
	{
		List<ProvinceModel> models = countryService.getCountryProvinces(CountryEntity.AUSTRALIA);
		
		assertEquals("Should only be 1 object in the collection", 1, models.size());
		assertEquals("There is a mismatch in the IDs", ProvinceEntity.NSW, models.get(0).getProvinceId());
		assertEquals("There is a mismatch in the names", nswName, models.get(0).getProvinceName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getCountryHolidaysNullCountry()
	{
		countryService.getPublicHolidays(null, null);
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void getCountryHolidaysInvalidCountry()
	{
		countryService.getPublicHolidays(invalidCountry, null);
	}
	
	@Test
	public void getCountryHolidaysNational()
	{
		List<PublicHolidayModel> models = countryService.getPublicHolidays(CountryEntity.AUSTRALIA, null);
		
		assertEquals("Should only be 1 object in the collection", 1, models.size());
		assertEquals("There is a mismatch in the names", newYearsHolidayName, models.get(0).getName());
		assertEquals("There is a mismatch in the dates", newYearsHolidayDay, models.get(0).getHolidayDate());
		assertEquals("There is a mismatch in the national holiday value", true, models.get(0).getNationalHoliday());
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void getCountryHolidaysInvalidProvince()
	{
		countryService.getPublicHolidays(CountryEntity.AUSTRALIA, invalidProvince);
	}
	
	@Test
	public void getCountryHolidaysProvince()
	{
		List<PublicHolidayModel> models = countryService.getPublicHolidays(CountryEntity.AUSTRALIA, ProvinceEntity.NSW);
		
		assertEquals("Should only be 2 objects in the collection", 2, models.size());
		assertEquals("There is a mismatch in the names", newYearsHolidayName, models.get(0).getName());
		assertEquals("There is a mismatch in the dates", newYearsHolidayDay, models.get(0).getHolidayDate());
		assertEquals("There is a mismatch in the national holiday value", true, models.get(0).getNationalHoliday());
		assertEquals("There is a mismatch in the names", newYearsHolidayName2, models.get(1).getName());
		assertEquals("There is a mismatch in the dates", newYearsHolidayDay2, models.get(1).getHolidayDate());
		assertEquals("There is a mismatch in the national holiday value", false, models.get(1).getNationalHoliday());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getHolidayNullCountry()
	{
		countryService.getPublicHoliday(null, newYearsHolidayDay, null);
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void getHolidayInvalidCountry()
	{
		countryService.getPublicHoliday(invalidCountry, newYearsHolidayDay, null);
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void getHolidayInvalidProvince()
	{
		countryService.getPublicHoliday(CountryEntity.AUSTRALIA, newYearsHolidayDay, invalidProvince);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getHolidayInvalidDate()
	{
		countryService.getPublicHoliday(CountryEntity.AUSTRALIA, invalidDate, null);
	}
	
	@Test
	public void getHoliday()
	{
		PublicHolidayModel model = countryService.getPublicHoliday(CountryEntity.AUSTRALIA, newYearsHolidayDay, null);
		
		assertEquals("There is a mismatch in the names", newYearsHolidayName, model.getName());
		assertEquals("There is a mismatch in the dates", newYearsHolidayDay, model.getHolidayDate());
	}
	
	@Test
	public void getHolidayProvince()
	{
		PublicHolidayModel model = countryService.getPublicHoliday(CountryEntity.AUSTRALIA, newYearsHolidayDay2, ProvinceEntity.NSW);
		
		assertEquals("There is a mismatch in the names", newYearsHolidayName2, model.getName());
		assertEquals("There is a mismatch in the dates", newYearsHolidayDay2, model.getHolidayDate());
	}
	
	@Test
	public void getHolidayNone()
	{
		PublicHolidayModel model = countryService.getPublicHoliday(CountryEntity.AUSTRALIA, noHolidayDay, null);
		
		assertNull(model);
	}
}
