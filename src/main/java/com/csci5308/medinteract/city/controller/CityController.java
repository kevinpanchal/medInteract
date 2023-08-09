package com.csci5308.medinteract.city.controller;
import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.city.service.CityService;
import com.csci5308.medinteract.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/city")
public class CityController {
    private final CityService cityServiceImpl;

    @Autowired
    public CityController(CityService cityServiceImpl){
        this.cityServiceImpl = cityServiceImpl;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity fetchAll()
    {
        List<CityModel> cityModelList= cityServiceImpl.fetchAll();
        Response res = new Response(cityModelList, false, "All cities fetched successfully");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }



    @PostMapping("/city_id")
    public ResponseEntity getCityId(@RequestBody CityModel cityModel)
    {
        Long cityId = cityServiceImpl.getCityId(cityModel);
        Response res = new Response(cityId, false, "City Id found");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/city_name")
    public ResponseEntity getCityName(@RequestBody CityModel cityModel)
    {
        Object cityName = cityServiceImpl.getCityName(cityModel);
        Response res = new Response(cityName, false, "City name found");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/city_name_with_province")
    public ResponseEntity getCityIdWithProvince(@RequestBody CityModel cityModel)
    {
        Object cityId = cityServiceImpl.getCityIdWithProvince(cityModel);
        Response res = new Response(cityId, false, "City Id found");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }
}
