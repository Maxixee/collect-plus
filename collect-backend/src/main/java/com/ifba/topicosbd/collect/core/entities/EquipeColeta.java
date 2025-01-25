package com.ifba.topicosbd.collect.core.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @OneToMany(mappedBy = "equipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TrabalhadorEquipeColeta> trabalhadorEquipeColetas = new HashSet<>();
}