package com.ifba.topicosbd.collect.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoResponseDto {

    private Long id;

    private String cep;

    private String cidade;

    private String rua;

    private Integer numero;

    private String complemento;
}
