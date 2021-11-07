package com.avk.service;

import com.avk.common.ObjectNotFoundException;
import com.avk.database.CountryEntity;
import com.avk.database.ProvinceEntity;
import com.avk.database.PublicHolidayEntity;
import com.avk.database.WorkDayRule;
import com.avk.model.*;
import com.avk.repository.CountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public CountryModel getCountry(String id) {
        CountryEntity entity = validateCountry(id);
        return CountryModelBuilder.buildModel(entity.getId(), entity.getName());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProvinceModel> getCountryProvinces(String id) {
        CountryEntity entity = validateCountry(id);

        List<ProvinceModel> models = new ArrayList<>();

        entity.getProvinceEntities().forEach(provinceEntity -> models.add(ProvinceModelBuilder.buildModel(provinceEntity.getProvinceId(), provinceEntity.getProvinceName())));

        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryModel> getCountries() {
        Iterable<CountryEntity> entities = countryRepository.findAll();

        List<CountryModel> models = new ArrayList<>();

        entities.forEach(entity -> models.add(CountryModelBuilder.buildModel(entity.getId(), entity.getName())));

        return models;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicHolidayModel> getPublicHolidays(String countryId, String provinceId) {
        CountryEntity entity = validateCountry(countryId);

        SortedSet<PublicHolidayEntity> entities = new TreeSet<>();
        List<PublicHolidayModel> result = new ArrayList<>();

        if (provinceId != null) {
            ProvinceEntity province = countryRepository.getProvince(countryId, provinceId);
            if (province != null) {
                entities.addAll(province.getPublicHolidayEntities());
            } else
                throw new ObjectNotFoundException("No province found for the id: " + provinceId + " for country: " + entity.getName());
        }

        entities.addAll(countryRepository.getNationalPublicHolidays(countryId));

        entities.forEach(publicHolidayEntity -> result.add(PublicHolidayModelBuilder.buildModel(publicHolidayEntity.getName(), publicHolidayEntity.getHolidayDateValue(), publicHolidayEntity.getNationalHoliday())));

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public PublicHolidayModel getPublicHoliday(String id, String date, String provinceId) {
        List<PublicHolidayModel> holidays = getPublicHolidays(id, provinceId);

        PublicHolidayModel holiday = null;

        for (PublicHolidayModel holidayModel : holidays) {
            if (holidayModel.getHolidayDate().equals(date))
                holiday = holidayModel;
        }

        return holiday;
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getBusinessDaysIn(String countryId, String startDate, Integer days, String provinceId) {
        LocalDate localDate = LocalDate.parse(startDate);

        int addedDays = 0;
        List<String> businessDays = new LinkedList<>();

        CountryEntity entity = validateCountry(countryId);

        List<PublicHolidayModel> publicHolidayModels = getPublicHolidays(countryId, provinceId);

        WorkDayRule rule = entity.getWorkDayRule();

        while (addedDays < days) {
            localDate = localDate.plusDays(1);
            if (rule.isBusinessDay(localDate) && !isHoliday(localDate, publicHolidayModels)) {
                businessDays.add(localDate.toString());
            }
            addedDays++;
        }

        return businessDays;
    }

    private boolean isHoliday(LocalDate localDate, List<PublicHolidayModel> publicHolidayModels) {
        return publicHolidayModels.stream()
                .map(PublicHolidayModel::getHolidayDate)
                .anyMatch(localDate.toString()::equals);
    }

    private CountryEntity validateCountry(String countryId) {
        if (countryId == null)
            throw new IllegalArgumentException("Country must be provided to get public holidays for");

        Optional<CountryEntity> entity = countryRepository.findById(countryId);

        if (entity.isEmpty())
            throw new ObjectNotFoundException("No country found for the id: " + countryId);

        return entity.get();
    }
}
