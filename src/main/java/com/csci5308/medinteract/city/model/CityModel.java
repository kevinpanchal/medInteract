package com.csci5308.medinteract.city.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="cities")
public class CityModel {

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLat() {
        return lat;
    }

//    public void setLat(Double lat) {
//        this.lat = lat;
//    }

    public Double getLng() {
        return lng;
    }

//    public void setLng(Double lng) {
//        this.lng = lng;
//    }

    public Double getPopulation() {
        return population;
    }

//    public void setPopulation(Double population) {
//        this.population = population;
//    }

    public Double getDensity() {
        return density;
    }

//    public void setDensity(Double density) {
//        this.density = density;
//    }

    public String getTimezone() {
        return timezone;
    }

//    public void setTimezone(String timezone) {
//        this.timezone = timezone;
//    }

    public Integer getRanking() {
        return ranking;
    }

//    public void setRanking(Integer ranking) {
//        this.ranking = ranking;
//    }

    public String getPostal() {
        return postal;
    }

//    public void setPostal(String postal) {
//        this.postal = postal;
//    }

    public Boolean getActive() {
        return isActive;
    }

//    public void setActive(Boolean active) {
//        isActive = active;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cities_generator")
    @SequenceGenerator(name="cities_generator", sequenceName = "cities_seq", allocationSize=100)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    @NotNull
    private String city;
    @NotBlank
    @NotNull
    private Double lat;
    @NotBlank
    @NotNull
    private Double lng;
    @NotBlank
    @NotNull
    private Double population;
    @NotBlank
    @NotNull
    private Double density;
    @NotBlank
    @NotNull
    private String timezone;
    @NotBlank
    @NotNull
    private Integer ranking;
    @NotBlank
    @NotNull
    private String postal;
    @NotBlank
    @NotNull
    private Boolean isActive;
    @NotBlank
    @NotNull
    private Long province_mapping;
    public Long getProvince_mapping() {
        return province_mapping;
    }

    public void setProvince_mapping(Long province_mapping) {
        this.province_mapping = province_mapping;
    }

}
