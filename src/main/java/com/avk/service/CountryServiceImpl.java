package com.avk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avk.common.ObjectNotFoundException;
import com.avk.database.CountryEntity;
import com.avk.database.ProvinceEntity;
import com.avk.database.PublicHolidayEntity;
import com.avk.model.CountryModel;
import com.avk.model.CountryModelBuilder;
import com.avk.model.ProvinceModel;
import com.avk.model.ProvinceModelBuilder;
import com.avk.model.PublicHolidayModel;
import com.avk.model.PublicHolidayModelBuilder;
import com.avk.repository.CountryRepository;

@Service
public class CountryServiceImpl implements CountryService
{
	private final CountryRepository countryRepository;
	
	private final Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
	
	public CountryServiceImpl(CountryRepository countryRepository)
	{
		this.countryRepository = countryRepository;
	}
	
	@Override
	@Transactional(readOnly = true)
	public CountryModel getCountry(String id)
	{
		CountryEntity entity = validateCountry(id);
		return CountryModelBuilder.buildModel(entity.getId(), entity.getName());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<ProvinceModel> getCountryProvinces(String id)
	{
		CountryEntity entity = validateCountry(id);
		
		List<ProvinceModel> models = new ArrayList<>();
		
		entity.getProvinceEntities().forEach(provinceEntity -> models.add(ProvinceModelBuilder.buildModel(provinceEntity.getProvinceId(), provinceEntity.getProvinceName())));
		
		return models;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CountryModel> getCountries()
	{
		Iterable<CountryEntity> entities = countryRepository.findAll();
		
		List<CountryModel> models = new ArrayList<>();
		
		entities.forEach(entity -> models.add(CountryModelBuilder.buildModel(entity.getId(), entity.getName())));
		
		return models;
	}

	@Override
	@Transactional(readOnly = true)
	public List<PublicHolidayModel> getPublicHolidays(String countryId, String provinceId)
	{
		CountryEntity entity = validateCountry(countryId);
		
		SortedSet<PublicHolidayEntity> entities = new TreeSet<>();
		List<PublicHolidayModel> result = new ArrayList<>();
		
		if (provinceId != null)
		{
			ProvinceEntity province = countryRepository.getProvince(countryId, provinceId);
			if (province != null)
			{
				province.getPublicHolidayEntities().forEach(publicHolidayEntity -> entities.add(publicHolidayEntity));
			}
			else
				throw new ObjectNotFoundException("No province found for the id: " + provinceId + " for country: " + entity.getName());
		}
		
		countryRepository.getNationalPublicHolidays(countryId).forEach(publicHolidayEntity -> entities.add(publicHolidayEntity));
		
		entities.forEach(publicHolidayEntity -> result.add(PublicHolidayModelBuilder.buildModel(publicHolidayEntity.getName(), publicHolidayEntity.getHolidayDateValue(), publicHolidayEntity.getNationalHoliday())));

		return result;
	}
	
	@Override
	@Transactional
	public PublicHolidayModel getPublicHoliday(String id, String date, String provinceId)
	{
		if (!p.matcher(date).matches())
			throw new IllegalArgumentException("Date must be in the format YYYY-MM-DD");
		
		List<PublicHolidayModel> holidays = getPublicHolidays(id, provinceId);		
		
		PublicHolidayModel holiday = null;
		
		for (PublicHolidayModel holidayModel : holidays)
		{
			if (holidayModel.getHolidayDate().equals(date))
				holiday = holidayModel;
		}
		
		return holiday;
	}
	
	private CountryEntity validateCountry(String countryId)
	{
		if (countryId == null)
			throw new IllegalArgumentException("Country must be provided to get public holidays for");
		
		CountryEntity entity = countryRepository.findOne(countryId);
		
		if (entity == null)
			throw new ObjectNotFoundException("No country found for the id: " + countryId);
		
		return entity;
	}
}
