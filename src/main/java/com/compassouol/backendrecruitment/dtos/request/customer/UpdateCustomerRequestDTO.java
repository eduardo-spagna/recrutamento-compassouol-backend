package com.compassouol.backendrecruitment.dtos.request.customer;

import java.io.Serializable;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Size(max = 90, message = "O nome do cliente deve conter no máximo 90 caracteres")
    @ApiModelProperty(example = "Eduardo S Spagna", required = true)
    private String customerName;
}
