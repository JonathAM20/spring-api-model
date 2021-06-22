package br.com.domain.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_card_situation")
public class CardSituation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card_situation")
    private Long id;

    @Column(name = "ds_situation", nullable = false, length = 50)
    private String situation;
}