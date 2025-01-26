package com.ifba.topicosbd.collect.core.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "equipe_coleta")
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class EquipeColeta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(unique = true)
    private String placaDoCarro;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "trabalhador_equipe_coleta",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "trabalhador_id")
    )
    @Setter(AccessLevel.NONE)
    private Set<Trabalhador> trabalhadores = new HashSet<>();
}