package com.ifba.topicosbd.collect.api.dto;

import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import com.ifba.topicosbd.collect.core.entities.PontoDeColeta;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColetaResponseDto {

    private Long id;
    private PontoDeColetaResponseDto pontoColeta;
    private EquipeColetaResponseDto equipeColeta;
    private Double frete;

}
