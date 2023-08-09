package com.csci5308.medinteract.city.service;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.city.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository){
        this.cityRepository = cityRepository;
    }
    @Override
    public List<CityModel> fetchAll() {
        return cityRepository.findAll();
    }

    public Long getCityId(CityModel cityModel)
    {
        String city = cityModel.getCity();
        List<CityModel> cityModelList = cityRepository.findByCity(city);
        return cityModelList.get(0).getId();
    }

    public Object getCityName(CityModel cityModel)
    {
        Long cityId = cityModel.getId();
        Optional<CityModel> cityModelList = cityRepository.findById(cityId);
        //List<Object> cityModelList = cityRepository.findByIdWithProvince(cityId);
        return cityModelList.get().getCity();
    }

    public Object getCityIdWithProvince(CityModel cityModel)
    {
        Long city = cityModel.getId();
        List<Object> cityModelList = cityRepository.findByCityWithProvince(city);
        return cityModelList;
    }
}


