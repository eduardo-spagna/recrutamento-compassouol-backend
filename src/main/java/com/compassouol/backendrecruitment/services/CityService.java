package com.compassouol.backendrecruitment.services;

import java.util.List;
import java.util.Optional;

import com.compassouol.backendrecruitment.dtos.request.city.CreateCityRequestDTO;
import com.compassouol.backendrecruitment.models.City;
import com.compassouol.backendrecruitment.models.State;
import com.compassouol.backendrecruitment.repositories.CityRepository;
import com.compassouol.backendrecruitment.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    @Autowired
    private CityRepository cityRepository;

    public City create(CreateCityRequestDTO createCity, State state) {
        City city = new City();

        StringUtil stringUtil = new StringUtil();

        city.setCityName(createCity.getCityName());
        city.setCityNameNormalized(stringUtil.normalizeString(createCity.getCityName()));
        city.setState(state);

        return cityRepository.save(city);
    }

    public List<City> findAllWithSearch(String search) {
        if (search == null) {
            search = "";
        } else {
            StringUtil stringUtil = new StringUtil();
            search = stringUtil.normalizeString(search);
        }

        return cityRepository.findAllWithSearch(search);
    }

    public City findById(long id) {
        Optional<City> city = cityRepository.findById(id);

        if (city.isPresent() == true) {
            return city.get();
        }

        return null;
    }
}
