package com.ifba.topicosbd.collect.core.repository;

import com.ifba.topicosbd.collect.core.entities.Trabalhador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrabalhadorRepository extends JpaRepository<Trabalhador, Long> {

    Optional<Trabalhador> findByCPF(String CPF);

}
