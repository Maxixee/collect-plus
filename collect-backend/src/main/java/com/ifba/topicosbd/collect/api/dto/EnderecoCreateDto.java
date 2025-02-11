package com.ifba.topicosbd.collect.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoCreateDto {

    @NotBlank(message = "O campo CEP é obrigatório.")
    private String cep;

    @NotBlank(message = "O campo cidade é obrigatório.")
    private String cidade;

    @NotBlank(message = "O campo rua é obrigatório.")
    private String rua;

    private Integer numero;

    @NotBlank(message = "O campo complemento é obrigatório.")
    private String complemento;

}
