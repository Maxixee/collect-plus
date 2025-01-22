package com.ifba.topicosbd.collect.core.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "equipe_coleta")
public class EquipeColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trabalhador> trabalhadores;
}