package com.ifba.topicosbd.collect.api.mapper;

import com.ifba.topicosbd.collect.api.dto.EnderecoCreateDto;
import com.ifba.topicosbd.collect.api.dto.EnderecoResponseDto;
import com.ifba.topicosbd.collect.core.entities.Endereco;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnderecoMapper {

    public static Endereco toEntity(EnderecoCreateDto dto) {
        return new ModelMapper().map(dto, Endereco.class);
    }

    public static EnderecoResponseDto toDto(Endereco endereco) {
        return new ModelMapper().map(endereco, EnderecoResponseDto.class);
    }
}
