package com.compassouol.backendrecruitment.controllers;

import java.util.ArrayList;
import java.util.List;

import com.compassouol.backendrecruitment.dtos.response.ResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.state.ShowStateResponseDTO;
import com.compassouol.backendrecruitment.models.State;
import com.compassouol.backendrecruitment.services.StateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/v1/states")
public class StateController {
    @Autowired
    private StateService stateService;

    @GetMapping
    @ApiOperation(value = "List all states")
    public ResponseEntity<ResponseDTO<List<ShowStateResponseDTO>>> listAll() {
        try {
            List<State> states = stateService.findAll();

            List<ShowStateResponseDTO> showStateListDTO = new ArrayList<>();
            for (State state : states) {
                showStateListDTO.add(
                        new ShowStateResponseDTO(state.getStateId(), state.getStateName(), state.getStateShortName()));
            }

            ResponseDTO<List<ShowStateResponseDTO>> response = new ResponseDTO<List<ShowStateResponseDTO>>(
                    "state/successfully-listed", "Listagem de estados realizada com sucesso", showStateListDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<List<ShowStateResponseDTO>> response = new ResponseDTO<List<ShowStateResponseDTO>>(
                    "state/listing-error", "Ocorreu um erro ao listar os estados", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
