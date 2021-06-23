package br.com.domain.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    private Long id;

    @Column(name = "cd_pan", nullable = false, length = 16)
    private String pan;

    @ManyToOne
    @JoinColumn(name="id_card_situation", nullable=false)
    private CardSituation cardSituation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_customer", nullable=false)
    private Customer customer;
}