package br.com.domain.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Long id;

    @NotNull
    @Size(min = 2, max = 11)
    @Column(name = "cd_personal", nullable = false, length = 11)
    private String personalCode;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "ds_first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "ds_last_name", nullable = false, length = 50)
    private String lastName;
}