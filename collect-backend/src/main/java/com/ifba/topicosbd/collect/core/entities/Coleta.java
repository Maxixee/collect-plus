package com.ifba.topicosbd.collect.core.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coleta")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Coleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ponto_coleta_id", nullable = false)
    private PontoDeColeta pontoColeta;

    @ManyToOne
    @JoinColumn(name = "equipe_id", nullable = false)
    private EquipeColeta equipeColeta;

    private Double frete;
}
