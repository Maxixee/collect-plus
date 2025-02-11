package com.ifba.topicosbd.collect.api.mapper;

import com.ifba.topicosbd.collect.api.dto.PontoDeColetaCreateDto;
import com.ifba.topicosbd.collect.api.dto.PontoDeColetaResponseDto;
import com.ifba.topicosbd.collect.core.entities.PontoDeColeta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PontoDeColetaMapper {

    public static PontoDeColeta toEntity(PontoDeColetaCreateDto dto) {
        return new ModelMapper().map(dto, PontoDeColeta.class);
    }

    public static PontoDeColetaResponseDto toDto(PontoDeColeta pontoDeColeta) {
        return new ModelMapper().map(pontoDeColeta, PontoDeColetaResponseDto.class);
    }
}
