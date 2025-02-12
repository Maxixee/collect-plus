package com.ifba.topicosbd.collect.api.dto;

import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import com.ifba.topicosbd.collect.core.entities.PontoDeColeta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColetaCreateDto {

    @NotNull(message = "O campo ponto de coleta é obrigatório.")
    private Long pontoColeta;

    @NotNull(message = "O campo equipe de coleta é obrigatório.")
    private Long equipeColeta;

    @NotNull(message = "O campo frete é obrigatório.")
    private Double frete;
}
