package com.avk.model;

import org.springframework.hateoas.ResourceSupport;

public class CountryModel extends ResourceSupport
{
	private String countryId;
	private String countryName;
	
	public String getCountryId()
	{
		return countryId;
	}
	
	public void setCountryId(String countryId)
	{
		this.countryId = countryId;
	}
	
	public String getCountryName()
	{
		return countryName;
	}
	
	public void setCountryName(String countryName)
	{
		this.countryName = countryName;
	}
	
	public void buildLinks()
	{
		
	}
}
