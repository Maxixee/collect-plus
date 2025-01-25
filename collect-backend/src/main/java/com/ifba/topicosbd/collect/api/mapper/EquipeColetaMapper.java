package com.ifba.topicosbd.collect.api.mapper;

import com.ifba.topicosbd.collect.api.dto.EquipeColetaCreateDto;
import com.ifba.topicosbd.collect.api.dto.EquipeColetaResponseDto;
import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EquipeColetaMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static EquipeColeta toEntity(EquipeColetaCreateDto createDto) {
        return modelMapper.map(createDto, EquipeColeta.class);
    }

    public static EquipeColetaResponseDto toDto(EquipeColeta equipeColeta) {
        return modelMapper.map(equipeColeta, EquipeColetaResponseDto.class);
    }
}
