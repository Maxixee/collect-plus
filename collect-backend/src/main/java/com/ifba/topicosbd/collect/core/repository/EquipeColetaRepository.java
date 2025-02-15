package com.ifba.topicosbd.collect.core.repository;

import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import com.ifba.topicosbd.collect.core.repository.projection.EquipeColetaProjection;
import com.ifba.topicosbd.collect.core.repository.projection.EquipeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EquipeColetaRepository extends JpaRepository<EquipeColeta, Long> {

    Optional<EquipeColeta> findByPlacaDoCarro(String placa);
    Boolean existsByPlacaDoCarro(String placa);
    Page<EquipeColetaProjection> findByTrabalhadoresId(Long id, Pageable pageable);

    @Query("select e from EquipeColeta e")
    Page<EquipeProjection> findAllPageable(Pageable pageable);
}
