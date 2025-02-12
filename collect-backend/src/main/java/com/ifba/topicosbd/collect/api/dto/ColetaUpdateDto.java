package com.ifba.topicosbd.collect.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColetaUpdateDto {

    @NotNull(message = "O campo ponto de coleta é obrigatório.")
    private Long pontoColetaId;

    @NotNull(message = "O campo equipe de coleta é obrigatório.")
    private Long equipeColetaId;

    @NotNull(message = "O campo frete é obrigatório.")
    private Double frete;

}
