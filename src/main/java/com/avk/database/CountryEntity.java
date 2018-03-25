package com.avk.database;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "country")
public class CountryEntity
{
	public static final String AUSTRALIA = "AU";
	public static final String NEW_ZEALAND = "NZ";
	
	@Id
	@Column
	private String id;
	
	@Column
	private String name;
	
	@OneToMany(mappedBy = "countryEntity")
	private Set<ProvinceEntity> provinceEntities = new HashSet<>();
	
	@OneToMany(mappedBy = "countryEntity")
	private Set<PublicHolidayEntity> publicHolidayEntities = new HashSet<>();

	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Set<ProvinceEntity> getProvinceEntities()
	{
		return provinceEntities;
	}
	
	public Set<PublicHolidayEntity> getPublicHolidays()
	{
		return publicHolidayEntities;
	}

	public WorkDayRule getWorkDayRule()
	{
		if (getId().equals(AUSTRALIA) ||
				getId().equals(NEW_ZEALAND))
		{
			return new AustraliaWorkingDayRuleRules();
		}
		else
			throw new IllegalArgumentException("Unknown working day rules for country: " + getId());
	}
}
