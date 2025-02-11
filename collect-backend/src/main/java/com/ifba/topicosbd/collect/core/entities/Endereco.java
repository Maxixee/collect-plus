package com.ifba.topicosbd.collect.core.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "endereco")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "cep", nullable = false)
    private String cep;
    @Column(name = "cidade", nullable = false)
    private String cidade;
    @Column(name = "rua", nullable = false)
    private String rua;
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "complemento")
    private String complemento;

}
