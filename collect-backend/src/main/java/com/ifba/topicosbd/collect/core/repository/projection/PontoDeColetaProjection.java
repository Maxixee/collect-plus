package com.ifba.topicosbd.collect.core.repository.projection;

public interface PontoDeColetaProjection {
    Long getId();
    String getTipoLixo();
    EnderecoProjection getEndereco();
}
