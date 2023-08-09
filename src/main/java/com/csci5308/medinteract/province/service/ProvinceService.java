package com.csci5308.medinteract.province.service;

import com.csci5308.medinteract.province.model.ProvinceModel;

import java.util.List;

public interface ProvinceService {

    List<ProvinceModel> fetchAll();
    public Long getProvinceId(ProvinceModel provinceModel);
}
