package com.ifba.topicosbd.collect.core.repository;

import com.ifba.topicosbd.collect.core.entities.Trabalhador;
import com.ifba.topicosbd.collect.core.repository.projection.TrabalhadorProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TrabalhadorRepository extends JpaRepository<Trabalhador, Long> {

    Optional<Trabalhador> findByCPF(String CPF);
    Page<TrabalhadorProjection> findByEquipesId(Long id, Pageable pageable);

    @Query("select t from Trabalhador t")
    Page<TrabalhadorProjection> findAllPageable(Pageable pageable);
}
