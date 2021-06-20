package br.com.domain.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Pessoa")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobreNome;
}