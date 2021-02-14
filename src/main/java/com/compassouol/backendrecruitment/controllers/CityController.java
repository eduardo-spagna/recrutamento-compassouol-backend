package com.compassouol.backendrecruitment.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.compassouol.backendrecruitment.dtos.request.city.CreateCityRequestDTO;
import com.compassouol.backendrecruitment.dtos.response.ResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.city.ShowCityResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.state.ShowStateResponseDTO;
import com.compassouol.backendrecruitment.models.City;
import com.compassouol.backendrecruitment.models.State;
import com.compassouol.backendrecruitment.services.CityService;
import com.compassouol.backendrecruitment.services.StateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/cities")
public class CityController {
    @Autowired
    private CityService cityService;

    @Autowired
    private StateService stateService;

    @PostMapping
    @ApiOperation(value = "Create a city")
    public ResponseEntity<ResponseDTO<ShowCityResponseDTO>> create(
            @Valid @RequestBody CreateCityRequestDTO createCity) {
        try {
            State state = stateService.findById(createCity.getStateId());

            if (state == null) {
                ResponseDTO<ShowCityResponseDTO> response = new ResponseDTO<ShowCityResponseDTO>(
                        "city/state-not-found", "Estado não encontrado", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            City newCity = cityService.create(createCity, state);

            ShowStateResponseDTO showStateDTO = new ShowStateResponseDTO(state.getStateId(), state.getStateName(),
                    state.getStateShortName());
            ShowCityResponseDTO showCityResponseDTO = new ShowCityResponseDTO(newCity.getCityId(),
                    newCity.getCityName(), showStateDTO);

            ResponseDTO<ShowCityResponseDTO> response = new ResponseDTO<ShowCityResponseDTO>(
                    "city/successfully-created", "Cidade criada com sucesso", showCityResponseDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<ShowCityResponseDTO> response = new ResponseDTO<ShowCityResponseDTO>("city/creating-error",
                    "Ocorreu um erro ao criar a cidade", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    @ApiOperation(value = "List all cities (optional: search by city or state name)")
    public ResponseEntity<ResponseDTO<List<ShowCityResponseDTO>>> listAll(
            @RequestParam(value = "search", required = false) String search) {
        try {
            List<City> cities = cityService.findAllWithSearch(search);

            List<ShowCityResponseDTO> showCityListDTO = new ArrayList<>();
            for (City city : cities) {
                showCityListDTO.add(new ShowCityResponseDTO(city.getCityId(), city.getCityName(),
                        new ShowStateResponseDTO(city.getState().getStateId(), city.getState().getStateName(),
                                city.getState().getStateShortName())));
            }

            ResponseDTO<List<ShowCityResponseDTO>> response = new ResponseDTO<List<ShowCityResponseDTO>>(
                    "city/successfully-listed", "Listagem de cidades realizada com sucesso", showCityListDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<List<ShowCityResponseDTO>> response = new ResponseDTO<List<ShowCityResponseDTO>>(
                    "city/listing-error", "Ocorreu um erro ao listar as cidades", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
