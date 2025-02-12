package com.ifba.topicosbd.collect.api.mapper;

import com.ifba.topicosbd.collect.api.dto.ColetaCreateDto;
import com.ifba.topicosbd.collect.api.dto.ColetaResponseDto;
import com.ifba.topicosbd.collect.api.dto.EquipeColetaCreateDto;
import com.ifba.topicosbd.collect.api.dto.EquipeColetaResponseDto;
import com.ifba.topicosbd.collect.core.entities.Coleta;
import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import com.ifba.topicosbd.collect.core.entities.PontoDeColeta;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColetaMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Coleta toEntity(ColetaCreateDto createDto, PontoDeColeta pontoDeColeta, EquipeColeta equipeColeta) {
        Coleta coleta = modelMapper.map(createDto, Coleta.class);
        coleta.setPontoColeta(pontoDeColeta);
        coleta.setEquipeColeta(equipeColeta);
        return coleta;
    }

    public static ColetaResponseDto toDto(Coleta Coleta) {
        return modelMapper.map(Coleta, ColetaResponseDto.class);
    }

}
