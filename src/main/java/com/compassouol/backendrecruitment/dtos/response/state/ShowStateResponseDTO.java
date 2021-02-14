package com.compassouol.backendrecruitment.dtos.response.state;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShowStateResponseDTO {
    private long stateId;
    private String stateName;
    private String stateShortName;
}
