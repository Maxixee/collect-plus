package com.ifba.topicosbd.collect.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class EquipeColetaCreateDto {

    @NotBlank(message = "O campo placa do carro é obrigatório.")
    @Size(min = 7, max = 8, message = "A placa do carro deve ter 7 ou 8 carácteres")
    private String placaDoCarro;

}
