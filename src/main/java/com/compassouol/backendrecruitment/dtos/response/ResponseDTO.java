package com.compassouol.backendrecruitment.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    private String code;
    private String message;
    private T data;
}
