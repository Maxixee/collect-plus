package com.ifba.topicosbd.collect.api.dto;

import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class TrabalhadorResponseDto {

    private Long id;
    private String CPF;
    private String salario;
    private String nome;
    private EquipeColeta equipe;

}
