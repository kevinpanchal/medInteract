package com.csci5308.medinteract.province.controller;

import com.csci5308.medinteract.province.model.ProvinceModel;
import com.csci5308.medinteract.province.service.ProvinceService;
import com.csci5308.medinteract.Response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/province")
public class ProvinceController {
    private final ProvinceService provinceServiceImpl;

    @Autowired
    public ProvinceController(ProvinceService provinceServiceImpl){
        this.provinceServiceImpl = provinceServiceImpl;
    }

    @GetMapping("/fetchAll")
    public ResponseEntity fetchAll()
    {
        List<ProvinceModel> provinceModelList= provinceServiceImpl.fetchAll();
        Response res = new Response(provinceModelList, false, "All provinces fetched successfully");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping("/province_id")
    public ResponseEntity getProvinceId(@RequestBody ProvinceModel provinceModel)
    {
        Long provinceId = provinceServiceImpl.getProvinceId(provinceModel);
        Response res = new Response(provinceId, false, "Province Id found");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

}
