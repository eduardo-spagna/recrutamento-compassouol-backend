package com.compassouol.backendrecruitment.dtos.request.customer;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "O nome do cliente é obrigatório")
    @Size(max = 90, message = "O nome do cliente deve conter no máximo 90 caracteres")
    @ApiModelProperty(example = "Eduardo Spagna", required = true)
    private String customerName;

    @NotNull(message = "A data de nascimento é obrigatória")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @ApiModelProperty(example = "1999-06-05", required = true)
    private LocalDate customerBirthdate;

    @NotNull(message = "O ID do gênero é obrigatório")
    @ApiModelProperty(example = "1", required = true)
    private Long genderId;

    @NotNull(message = "O ID da cidade é obrigatório")
    @ApiModelProperty(example = "2", required = true)
    private Long cityId;
}
