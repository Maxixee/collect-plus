package com.ifba.topicosbd.collect.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TrabalhadorCreateDto {

    @NotBlank(message = "O campo CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve ter 11 digitos")
    private String CPF;
    @NotBlank(message = "O campo salário é obrigatório.")
    private String salario;
    @NotBlank(message = "O campo nome é obrigatório.")
    private String nome;

}
