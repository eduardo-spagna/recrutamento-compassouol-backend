package com.compassouol.backendrecruitment.controllers;

import javax.validation.Valid;

import com.compassouol.backendrecruitment.dtos.request.customer.CreateCustomerRequestDTO;
import com.compassouol.backendrecruitment.dtos.response.ResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.city.ShowCityResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.customer.CreateCustomerResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.gender.ShowGenderResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.state.ShowStateResponseDTO;
import com.compassouol.backendrecruitment.models.City;
import com.compassouol.backendrecruitment.models.Customer;
import com.compassouol.backendrecruitment.models.Gender;
import com.compassouol.backendrecruitment.services.CityService;
import com.compassouol.backendrecruitment.services.CustomerService;
import com.compassouol.backendrecruitment.services.GenderService;
import com.compassouol.backendrecruitment.utils.DateUtil;

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
@RequestMapping(value = "/api/v1/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private GenderService genderService;

    @Autowired
    private CityService cityService;

    @PostMapping
    @ApiOperation(value = "Create a customer")
    public ResponseEntity<ResponseDTO<CreateCustomerResponseDTO>> create(
            @Valid @RequestBody CreateCustomerRequestDTO createCustomer) {
        try {
            Gender gender = genderService.findById(createCustomer.getGenderId());

            if (gender == null) {
                ResponseDTO<CreateCustomerResponseDTO> response = new ResponseDTO<CreateCustomerResponseDTO>(
                        "customer/gender-not-found", "Gênero não encontrado", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            City city = cityService.findById(createCustomer.getCityId());

            if (city == null) {
                ResponseDTO<CreateCustomerResponseDTO> response = new ResponseDTO<CreateCustomerResponseDTO>(
                        "customer/city-not-found", "Cidade não encontrada", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Customer newCustomer = customerService.create(createCustomer, gender, city);

            int customerAge = DateUtil.getAgeFromBirthdate(newCustomer.getCustomerBirthdate());

            ShowGenderResponseDTO showGenderResponseDTO = new ShowGenderResponseDTO(gender.getGenderId(),
                    gender.getGenderDescription());
            ShowCityResponseDTO showCityResponseDTO = new ShowCityResponseDTO(city.getCityId(), city.getCityName(),
                    new ShowStateResponseDTO(city.getState().getStateId(), city.getState().getStateName(),
                            city.getState().getStateShortName()));
            CreateCustomerResponseDTO createCustomerResponseDTO = new CreateCustomerResponseDTO(
                    newCustomer.getCustomerId(), newCustomer.getCustomerName(), newCustomer.getCustomerBirthdate(),
                    customerAge, showGenderResponseDTO, showCityResponseDTO);

            ResponseDTO<CreateCustomerResponseDTO> response = new ResponseDTO<CreateCustomerResponseDTO>(
                    "customer/successfully-created", "Cliente criado com sucesso", createCustomerResponseDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<CreateCustomerResponseDTO> response = new ResponseDTO<CreateCustomerResponseDTO>(
                    "customer/creating-error", "Ocorreu um erro ao criar o cliente", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
