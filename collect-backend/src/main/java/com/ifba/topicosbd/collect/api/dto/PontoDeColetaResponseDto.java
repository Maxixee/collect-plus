package com.ifba.topicosbd.collect.api.dto;

import lombok.Data;

@Data
public class PontoDeColetaResponseDto {
    private Long id;
    private EnderecoResponseDto endereco;
    private String tipoLixo;
}

