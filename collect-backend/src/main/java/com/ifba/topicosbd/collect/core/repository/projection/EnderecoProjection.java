package com.ifba.topicosbd.collect.core.repository.projection;


public interface EnderecoProjection {
    Long getId();
    String getCep();
    String getCidade();
    String getRua();
    Integer getNumero();
    String getComplemento();
}
