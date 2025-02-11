package com.ifba.topicosbd.collect.core.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "ponto_coleta")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PontoDeColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @Column(name = "tipo_lixo", nullable = false)
    private String tipoLixo;
}
