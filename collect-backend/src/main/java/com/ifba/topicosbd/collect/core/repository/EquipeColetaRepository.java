package com.ifba.topicosbd.collect.core.repository;

import com.ifba.topicosbd.collect.core.entities.EquipeColeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipeColetaRepository extends JpaRepository<EquipeColeta, Long> {

    Optional<EquipeColeta> findByPlacaDoCarro(String placa);
    Boolean existsByPlacaDoCarro(String placa);

}
