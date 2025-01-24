package com.ifba.topicosbd.collect.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TrabalhadorUpdateDto {

    @NotBlank(message = "O campo nome é obrigatório.")
    private String nome;
    @NotBlank(message = "O campo salário é obrigatório.")
    private String salario;

}
