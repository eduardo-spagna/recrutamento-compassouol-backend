package com.compassouol.backendrecruitment.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDTO<T> {
    private String errorCode;
    private String errorAttribute;
    private String errorMessage;
    private T errorCurrentValue;
}