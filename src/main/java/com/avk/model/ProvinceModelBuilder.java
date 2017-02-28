package com.avk.model;

public class ProvinceModelBuilder
{
	public static ProvinceModel buildModel(String provinceId, String provinceName)
	{
		ProvinceModel model = new ProvinceModel();
		
		model.setProvinceId(provinceId);
		model.setProvinceName(provinceName);
		
		return model;
	}
}
