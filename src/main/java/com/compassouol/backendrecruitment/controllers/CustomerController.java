package com.compassouol.backendrecruitment.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.compassouol.backendrecruitment.dtos.request.customer.CreateCustomerRequestDTO;
import com.compassouol.backendrecruitment.dtos.response.ResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.city.ShowCityResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.customer.CreateCustomerResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.customer.ShowCustomerResponseDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    @ApiOperation(value = "List all customers (optional: search by customer name)")
    public ResponseEntity<ResponseDTO<List<ShowCustomerResponseDTO>>> listAll(
            @RequestParam(value = "search", required = false) String search) {
        try {
            List<Customer> customers = customerService.findAllWithSearch(search);

            List<ShowCustomerResponseDTO> showCustomerListDTO = new ArrayList<>();
            for (Customer customer : customers) {
                int customerAge = DateUtil.getAgeFromBirthdate(customer.getCustomerBirthdate());

                ShowGenderResponseDTO showGenderResponseDTO = new ShowGenderResponseDTO(
                        customer.getGender().getGenderId(), customer.getGender().getGenderDescription());

                ShowCityResponseDTO showCityResponseDTO = new ShowCityResponseDTO(customer.getCity().getCityId(),
                        customer.getCity().getCityName(),
                        new ShowStateResponseDTO(customer.getCity().getState().getStateId(),
                                customer.getCity().getState().getStateName(),
                                customer.getCity().getState().getStateShortName()));

                showCustomerListDTO.add(new ShowCustomerResponseDTO(customer.getCustomerId(),
                        customer.getCustomerName(), customer.getCustomerBirthdate(), customerAge, showGenderResponseDTO,
                        showCityResponseDTO));
            }

            ResponseDTO<List<ShowCustomerResponseDTO>> response = new ResponseDTO<List<ShowCustomerResponseDTO>>(
                    "customer/successfully-listed", "Listagem de clientes realizada com sucesso", showCustomerListDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<List<ShowCustomerResponseDTO>> response = new ResponseDTO<List<ShowCustomerResponseDTO>>(
                    "customer/listing-error", "Ocorreu um erro ao listar os clientes", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
