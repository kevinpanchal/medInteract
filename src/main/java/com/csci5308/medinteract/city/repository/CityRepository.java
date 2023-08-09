package com.csci5308.medinteract.city.repository;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.province.model.ProvinceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityModel, Long> {
    //@Query("SELECT c FROM CityModel c WHERE c.city= ':city'")
    List<CityModel> findByCity(String city);

    Optional<CityModel> findById(Long cityId);
    @Query("select c.city, p.name from CityModel c join ProvinceModel p on c.province_mapping = p.id WHERE c.id=?1")
    List<Object> findByCityWithProvince(Long id);

}
