package com.compassouol.backendrecruitment.controllers;

import javax.validation.Valid;

import com.compassouol.backendrecruitment.dtos.request.city.CreateCityRequestDTO;
import com.compassouol.backendrecruitment.dtos.response.ResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.city.CreateCityResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.state.ShowStateResponseDTO;
import com.compassouol.backendrecruitment.models.City;
import com.compassouol.backendrecruitment.models.State;
import com.compassouol.backendrecruitment.services.CityService;
import com.compassouol.backendrecruitment.services.StateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<ResponseDTO<CreateCityResponseDTO>> create(
            @Valid @RequestBody CreateCityRequestDTO createCity) {
        try {
            State state = stateService.findById(createCity.getStateId());

            if (state == null) {
                ResponseDTO<CreateCityResponseDTO> response = new ResponseDTO<CreateCityResponseDTO>(
                        "city/state-not-found", "Estado não encontrado", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            City newCity = cityService.create(createCity, state);

            ShowStateResponseDTO showStateDTO = new ShowStateResponseDTO(state.getStateId(), state.getStateName(),
                    state.getStateShortName());
            CreateCityResponseDTO createCityResponseDTO = new CreateCityResponseDTO(newCity.getCityId(),
                    newCity.getCityName(), showStateDTO);

            ResponseDTO<CreateCityResponseDTO> response = new ResponseDTO<CreateCityResponseDTO>(
                    "city/successfully-created", "Cidade criada com sucesso", createCityResponseDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<CreateCityResponseDTO> response = new ResponseDTO<CreateCityResponseDTO>("city/creating-error",
                    "Ocorreu um erro ao criar a cidade", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
