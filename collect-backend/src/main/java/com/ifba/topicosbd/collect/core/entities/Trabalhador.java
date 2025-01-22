package com.ifba.topicosbd.collect.core.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "trabalhador")
public class Trabalhador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String CPF;
    @Column(name = "salario", nullable = false)
    private String salario;
    @Column(name = "nome", nullable = false)
    private String nome;
    @ManyToOne
    @JoinColumn(name = "equipe_id", nullable = false)
    private EquipeColeta equipe;
}
