package com.compassouol.backendrecruitment.dtos.request.city;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "O nome da cidade é obrigatório")
    @Size(max = 40, message = "O nome da cidade deve conter no máximo 40 caracteres")
    @ApiModelProperty(example = "Londrina", required = true)
    private String cityName;

    @NotNull(message = "O ID do estado é obrigatório")
    @ApiModelProperty(example = "16", required = true)
    private Long stateId;
}
