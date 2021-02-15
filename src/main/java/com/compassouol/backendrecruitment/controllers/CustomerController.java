package com.compassouol.backendrecruitment.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.compassouol.backendrecruitment.dtos.request.customer.CreateCustomerRequestDTO;
import com.compassouol.backendrecruitment.dtos.request.customer.UpdateCustomerRequestDTO;
import com.compassouol.backendrecruitment.dtos.response.ErrorResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.ResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.city.ShowCityResponseDTO;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<ResponseDTO<?>> create(@Valid @RequestBody CreateCustomerRequestDTO createCustomer,
            BindingResult result) {
        try {
            if (result.hasErrors() == true) {
                ErrorResponseDTO<Object> errorResponseDTO = new ErrorResponseDTO<Object>(
                        result.getFieldError().getCode(), result.getFieldError().getField(),
                        result.getFieldError().getDefaultMessage(), result.getFieldError().getRejectedValue());
                ResponseDTO<ErrorResponseDTO<Object>> response = new ResponseDTO<ErrorResponseDTO<Object>>(
                        "customer/invalid-data", "Dados inválidos", errorResponseDTO);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Gender gender = genderService.findById(createCustomer.getGenderId());

            if (gender == null) {
                ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
                        "customer/gender-not-found", "Gênero não encontrado", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            City city = cityService.findById(createCustomer.getCityId());

            if (city == null) {
                ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
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
            ShowCustomerResponseDTO showCustomerResponseDTO = new ShowCustomerResponseDTO(newCustomer.getCustomerId(),
                    newCustomer.getCustomerName(), newCustomer.getCustomerBirthdate(), customerAge,
                    showGenderResponseDTO, showCityResponseDTO);

            ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
                    "customer/successfully-created", "Cliente criado com sucesso", showCustomerResponseDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
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

    @GetMapping("/{customerId}")
    @ApiOperation(value = "Details of a customer")
    public ResponseEntity<ResponseDTO<ShowCustomerResponseDTO>> listOne(@PathVariable long customerId) {
        try {
            Customer customer = customerService.findById(customerId);

            if (customer == null) {
                ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
                        "customer/customer-not-found", "Cliente não encontrado", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            int customerAge = DateUtil.getAgeFromBirthdate(customer.getCustomerBirthdate());

            ShowGenderResponseDTO showGenderResponseDTO = new ShowGenderResponseDTO(customer.getGender().getGenderId(),
                    customer.getGender().getGenderDescription());

            ShowCityResponseDTO showCityResponseDTO = new ShowCityResponseDTO(customer.getCity().getCityId(),
                    customer.getCity().getCityName(),
                    new ShowStateResponseDTO(customer.getCity().getState().getStateId(),
                            customer.getCity().getState().getStateName(),
                            customer.getCity().getState().getStateShortName()));

            ShowCustomerResponseDTO showCustomerResponseDTO = new ShowCustomerResponseDTO(customer.getCustomerId(),
                    customer.getCustomerName(), customer.getCustomerBirthdate(), customerAge, showGenderResponseDTO,
                    showCityResponseDTO);

            ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
                    "customer/successfully-listed", "Dados do cliente exibidos com sucesso", showCustomerResponseDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
                    "customer/listing-error", "Ocorreu um erro ao exibir os dados do cliente", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{customerId}")
    @ApiOperation(value = "Update a customer")
    public ResponseEntity<ResponseDTO<?>> update(@Valid @RequestBody UpdateCustomerRequestDTO updateCustomer,
            @PathVariable long customerId, BindingResult result) {
        try {
            if (result.hasErrors() == true) {
                ErrorResponseDTO<Object> errorResponseDTO = new ErrorResponseDTO<Object>(
                        result.getFieldError().getCode(), result.getFieldError().getField(),
                        result.getFieldError().getDefaultMessage(), result.getFieldError().getRejectedValue());
                ResponseDTO<ErrorResponseDTO<Object>> response = new ResponseDTO<ErrorResponseDTO<Object>>(
                        "customer/invalid-data", "Dados inválidos", errorResponseDTO);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Customer customer = customerService.findById(customerId);

            if (customer == null) {
                ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
                        "customer/customer-not-found", "Cliente não encontrado", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            Customer updatedCustomer = customerService.update(updateCustomer, customer);

            int customerAge = DateUtil.getAgeFromBirthdate(updatedCustomer.getCustomerBirthdate());

            ShowGenderResponseDTO showGenderResponseDTO = new ShowGenderResponseDTO(
                    updatedCustomer.getGender().getGenderId(), updatedCustomer.getGender().getGenderDescription());

            ShowCityResponseDTO showCityResponseDTO = new ShowCityResponseDTO(updatedCustomer.getCity().getCityId(),
                    updatedCustomer.getCity().getCityName(),
                    new ShowStateResponseDTO(updatedCustomer.getCity().getState().getStateId(),
                            updatedCustomer.getCity().getState().getStateName(),
                            updatedCustomer.getCity().getState().getStateShortName()));

            ShowCustomerResponseDTO showCustomerResponseDTO = new ShowCustomerResponseDTO(
                    updatedCustomer.getCustomerId(), updatedCustomer.getCustomerName(),
                    updatedCustomer.getCustomerBirthdate(), customerAge, showGenderResponseDTO, showCityResponseDTO);

            ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
                    "customer/successfully-updated", "Cliente atualizado com sucesso", showCustomerResponseDTO);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
                    "customer/listing-error", "Ocorreu um erro ao atualizar o cliente", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{customerId}")
    @ApiOperation(value = "Delete a customer")
    public ResponseEntity<?> delete(@PathVariable long customerId) {
        try {
            Customer customer = customerService.findById(customerId);

            if (customer == null) {
                ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
                        "customer/customer-not-found", "Cliente não encontrado", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            customerService.delete(customer);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseDTO<ShowCustomerResponseDTO> response = new ResponseDTO<ShowCustomerResponseDTO>(
                    "customer/deleting-error", "Ocorreu um erro ao excluir o cliente", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
