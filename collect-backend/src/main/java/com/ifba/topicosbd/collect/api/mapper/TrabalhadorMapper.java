package com.ifba.topicosbd.collect.api.mapper;

import com.ifba.topicosbd.collect.api.dto.TrabalhadorCreateDto;
import com.ifba.topicosbd.collect.api.dto.TrabalhadorResponseDto;
import com.ifba.topicosbd.collect.core.entities.Trabalhador;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrabalhadorMapper {

    public static Trabalhador toEntity(TrabalhadorCreateDto dto) {
        return new ModelMapper().map(dto, Trabalhador.class);
    }

    public static TrabalhadorResponseDto toDto(Trabalhador trabalhador) {
        return new ModelMapper().map(trabalhador, TrabalhadorResponseDto.class);
    }
    
}
