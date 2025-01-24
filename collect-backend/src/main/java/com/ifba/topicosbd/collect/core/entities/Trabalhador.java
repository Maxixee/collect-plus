package com.ifba.topicosbd.collect.core.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trabalhador")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
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
    @JoinColumn(name = "equipe_id")
    private EquipeColeta equipe;
}
