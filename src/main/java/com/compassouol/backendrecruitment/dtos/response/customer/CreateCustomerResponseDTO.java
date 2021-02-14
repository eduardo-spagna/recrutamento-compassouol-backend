package com.compassouol.backendrecruitment.dtos.response.customer;

import java.time.LocalDate;

import com.compassouol.backendrecruitment.dtos.response.city.ShowCityResponseDTO;
import com.compassouol.backendrecruitment.dtos.response.gender.ShowGenderResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCustomerResponseDTO {
    private long customerId;
    private String customerName;
    private LocalDate customerBirthdate;
    private int customerAge;
    private ShowGenderResponseDTO gender;
    private ShowCityResponseDTO city;
}
