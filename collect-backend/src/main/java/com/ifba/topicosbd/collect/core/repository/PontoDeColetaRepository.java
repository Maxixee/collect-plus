package com.ifba.topicosbd.collect.core.repository;

import com.ifba.topicosbd.collect.core.entities.PontoDeColeta;
import com.ifba.topicosbd.collect.core.repository.projection.PontoDeColetaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoDeColetaRepository extends JpaRepository<PontoDeColeta, Long> {
    Page<PontoDeColetaProjection> findByTipoLixo(String tipoLixo, Pageable pageable);
}
