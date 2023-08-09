package com.csci5308.medinteract.city.service;

import com.csci5308.medinteract.city.model.CityModel;

import java.util.List;

public interface CityService {
    List<CityModel> fetchAll();
    public Long getCityId(CityModel cityModel);
    public Object getCityName(CityModel cityModel);
    public Object getCityIdWithProvince(CityModel cityModel);
}
