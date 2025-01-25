package com.ifba.topicosbd.collect.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trabalhador_equipe_coleta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrabalhadorEquipeColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trabalhador_id", nullable = false)
    private Trabalhador trabalhador;

    @ManyToOne
    @JoinColumn(name = "equipe_id", nullable = false)
    private EquipeColeta equipe;

}
