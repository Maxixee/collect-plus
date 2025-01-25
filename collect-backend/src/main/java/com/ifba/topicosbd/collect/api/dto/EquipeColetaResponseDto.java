package com.ifba.topicosbd.collect.api.dto;

import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EquipeColetaResponseDto {

    private Long id;
    private String placaDoCarro;

}
