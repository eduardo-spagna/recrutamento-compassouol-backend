package com.compassouol.backendrecruitment.dtos.response.gender;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowGenderResponseDTO {
    private long genderId;
    private String genderDescription;
}
