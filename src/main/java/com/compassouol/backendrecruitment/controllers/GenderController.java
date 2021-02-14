package com.compassouol.backendrecruitment.controllers;

import java.util.ArrayList;
import java.util.List;

import com.compassouol.backendrecruitment.dtos.response.ResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.gender.ShowGenderResponseDTO;
import com.compassouol.backendrecruitment.models.Gender;
import com.compassouol.backendrecruitment.services.GenderService;

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
@RequestMapping(value = "/api/v1/genders")
public class GenderController {
    @Autowired
    private GenderService genderService;

    @GetMapping
    @ApiOperation(value = "List all genders")
    public ResponseEntity<ResponseDTO<List<ShowGenderResponseDTO>>> listAll() {
        try {
            List<Gender> genders = genderService.findAll();

            List<ShowGenderResponseDTO> showGenderListDTO = new ArrayList<>();
            for (Gender gender : genders) {
                showGenderListDTO.add(new ShowGenderResponseDTO(gender.getGenderId(), gender.getGenderDescription()));
            }

            ResponseDTO<List<ShowGenderResponseDTO>> response = new ResponseDTO<List<ShowGenderResponseDTO>>(
                    "gender/successfully-listed", "Listagem de gêneros realizada com sucesso", showGenderListDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<List<ShowGenderResponseDTO>> response = new ResponseDTO<List<ShowGenderResponseDTO>>(
                    "gender/listing-error", "Ocorreu um erro ao listar os gêneros", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
