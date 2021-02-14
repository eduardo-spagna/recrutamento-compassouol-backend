package com.compassouol.backendrecruitment.dtos.response.city;

import com.compassouol.backendrecruitment.dtos.response.state.ShowStateResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowCityResponseDTO {
    private long cityId;
    private String cityName;
    private ShowStateResponseDTO state;
}
