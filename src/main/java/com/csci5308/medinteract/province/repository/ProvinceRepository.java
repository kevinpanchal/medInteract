package com.csci5308.medinteract.province.repository;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.province.model.ProvinceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<ProvinceModel, Long> {
    List<ProvinceModel> findByName(String province);
}
