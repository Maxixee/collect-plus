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
public class TrabalhadorEquipeDto {

    @NotNull(message = "O campo equipe coleta id é obrigatório.")
    private Long equipeColetaId;
    @NotNull(message = "O campo trabalhador id é obrigatório.")
    private Long trabalhadorId;

}
