package com.ifba.topicosbd.collect.core.repository;

import com.ifba.topicosbd.collect.core.entities.Coleta;
import com.ifba.topicosbd.collect.core.repository.projection.ColetaProjection;
import com.ifba.topicosbd.collect.core.repository.projection.EnderecoProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColetaRepository extends JpaRepository<Coleta, Long> {

    @Query("SELECT c.id AS id, c.frete AS frete, c.pontoColeta.id AS pontoColetaId, c.equipeColeta.id AS equipeColetaId " +
            "FROM Coleta c " +
            "WHERE c.pontoColeta.id = :pontoColetaId")
    Page<ColetaProjection> findByPontoColetaId(@Param("pontoColetaId") Long pontoColetaId, Pageable pageable);

    Page<ColetaProjection> findAllProjectedBy(Pageable pageable);

}
