package br.com.domain.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ds_first_name", nullable = false)
    private String firstName;

    @Column(name = "ds_last_name", nullable = false)
    private String lastName;
}