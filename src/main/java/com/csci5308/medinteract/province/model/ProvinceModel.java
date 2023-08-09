package com.csci5308.medinteract.province.model;

import com.csci5308.medinteract.city.model.CityModel;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name="provinces")
public class ProvinceModel {
    @Id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "province_generator")
    @SequenceGenerator(name="province_generator", sequenceName = "province_seq", allocationSize=100)
    @Column(name = "id", nullable = false)
    private Long id;
    @NotBlank
    @NotNull
    private String abbrv;
    @NotBlank
    @NotNull
    private String name;
    @OneToMany(targetEntity = CityModel.class)
    @JoinColumn(name="province_mapping")
    private List<CityModel> cities;
    @NotBlank
    @NotNull
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbrv() {
        return abbrv;
    }

    public void setAbbrv(String abbrv) {
        this.abbrv = abbrv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityModel> getCities() {
        return cities;
    }

    public void setCities(List<CityModel> cities) {
        this.cities = cities;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

}
