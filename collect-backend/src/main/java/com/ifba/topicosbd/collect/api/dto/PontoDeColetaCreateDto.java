package com.ifba.topicosbd.collect.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PontoDeColetaCreateDto {

    @NotNull(message = "O ID do endereço é obrigatório.")
    private Long endereco;

    @NotNull(message = "O tipo de lixo é obrigatório.")
    private String tipoLixo;
}

