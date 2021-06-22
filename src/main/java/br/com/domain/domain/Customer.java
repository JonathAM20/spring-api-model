package br.com.domain.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Long id;

    @Column(name = "cd_personal", nullable = false, length = 11)
    private String personalCode;

    @Column(name = "ds_first_name", nullable = false, length = 50)
    private String name;

    @Column(name = "ds_last_name", nullable = false, length = 50)
    private String lastName;

    @OneToMany(mappedBy="customer")
    private List<Card> cards;
}