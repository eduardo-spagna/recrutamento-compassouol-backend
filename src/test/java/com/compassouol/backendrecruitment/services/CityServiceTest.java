package com.compassouol.backendrecruitment.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.compassouol.backendrecruitment.dtos.request.city.CreateCityRequestDTO;
import com.compassouol.backendrecruitment.models.City;
import com.compassouol.backendrecruitment.models.State;
import com.compassouol.backendrecruitment.repositories.CityRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CityServiceTest {
    @TestConfiguration
    static class CityServiceTestConfiguration {
        @Bean
        public CityService cityService() {
            return new CityService();
        }
    }

    @Autowired
    private CityService cityService;

    @MockBean
    private CityRepository cityRepository;

    @Before
    public void setup() {
        // states
        State stateOfSP = new State(1L, "São Paulo", "sao_paulo", "SP");
        State stateOfPR = new State(2L, "Paraná", "parana", "PR");
        State stateOfMG = new State(3L, "Minas Gerais", "minas_gerais", "MG");
        State stateOfRS = new State(4L, "Rio Grande do Sul", "rio_grande_do_sul", "RS");

        // cities
        City londrinaCity = new City(1L, "Londrina", "londrina", LocalDateTime.now(), LocalDateTime.now(), stateOfPR);
        City cornelioProcopioCity = new City(2L, "Cornélio Procópio", "cornelio_procopio", LocalDateTime.now(),
                LocalDateTime.now(), stateOfPR);
        City beloHorizonteCity = new City(3L, "Belo Horizonte", "belo_horizonte", LocalDateTime.now(),
                LocalDateTime.now(), stateOfMG);
        City santosCity = new City(4L, "Santos", "santos", LocalDateTime.now(), LocalDateTime.now(), stateOfSP);
        City portoAlegreCity = new City(5L, "Porto Alegre", "porto_alegre", LocalDateTime.now(), LocalDateTime.now(),
                stateOfRS);

        // all cities
        List<City> allCities = new ArrayList<City>();

        allCities.add(londrinaCity);
        allCities.add(cornelioProcopioCity);
        allCities.add(beloHorizonteCity);
        allCities.add(santosCity);
        allCities.add(portoAlegreCity);

        // cities with search
        List<City> citiesWithSearchByOn = new ArrayList<City>();
        citiesWithSearchByOn.add(londrinaCity);
        citiesWithSearchByOn.add(beloHorizonteCity);

        Mockito.when(cityRepository.findById(beloHorizonteCity.getCityId()))
                .thenReturn(java.util.Optional.of(beloHorizonteCity));
        Mockito.when(cityRepository.findAllWithSearch("")).thenReturn(allCities);
        Mockito.when(cityRepository.findAllWithSearch("on")).thenReturn(citiesWithSearchByOn);
        Mockito.when(cityRepository.save(Mockito.any(City.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    @DisplayName("Should return a city named Belo Horizonte")
    public void shouldReturnBeloHorizonte() {
        City findCityById = cityService.findById(3L);
        assertEquals("Belo Horizonte", findCityById.getCityName());
    }

    @Test
    @DisplayName("Should return null when it does not find the city")
    public void shouldReturnNullWhenDoesntExist() {
        City findCityById = cityService.findById(7L);
        assertNull(findCityById);
    }

    @Test
    @DisplayName("Should return five cities when not searched by city or state")
    public void shouldReturnFiveCitiesWithoutSearch() {
        List<City> allCities = cityService.findAllWithSearch(null);
        assertEquals(5, allCities.size());
    }

    @Test
    @DisplayName("Should return two cities when searched by city or state with 'on'")
    public void shouldReturnTwoCitiesWithSearch() {
        List<City> allCities = cityService.findAllWithSearch("on");
        assertEquals(2, allCities.size());
    }

    @Test
    @DisplayName("Should create a city")
    public void shouldCreateACity() {
        State stateOfRS = new State(4L, "Rio Grande do Sul", "rio_grande_do_sul", "RS");
        CreateCityRequestDTO createCity = new CreateCityRequestDTO("Porto Alegre", stateOfRS.getStateId());

        City newCity = cityService.create(createCity, stateOfRS);

        assertEquals("porto_alegre", newCity.getCityNameNormalized());
    }
}
