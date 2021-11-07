package com.avk.repository;

import com.avk.database.CountryEntity;
import com.avk.database.ProvinceEntity;
import com.avk.database.PublicHolidayEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource(exported = false)
public interface CountryRepository extends PagingAndSortingRepository<CountryEntity, String> {

    @Query("SELECT r from PublicHolidayEntity r WHERE countryEntity.id = :countryId AND nationalHoliday = TRUE")
    Set<PublicHolidayEntity> getNationalPublicHolidays(@Param("countryId") String countryId);

    @Query("SELECT r from ProvinceEntity r WHERE countryEntity.id = :countryId AND provinceId = :provinceId")
    ProvinceEntity getProvince(@Param("countryId") String countryId, @Param("provinceId") String provinceId);
}
