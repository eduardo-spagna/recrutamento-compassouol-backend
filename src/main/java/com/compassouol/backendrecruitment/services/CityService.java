package com.compassouol.backendrecruitment.services;

import com.compassouol.backendrecruitment.dtos.request.city.CreateCityRequestDTO;
import com.compassouol.backendrecruitment.models.City;
import com.compassouol.backendrecruitment.models.State;
import com.compassouol.backendrecruitment.repositories.CityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public City create(CreateCityRequestDTO createCity, State state) {
        City city = new City();

        city.setCityName(createCity.getCityName());
        city.setState(state);

        return cityRepository.save(city);
    }
}
