package br.com.domain.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "tb_card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    private Long id;

    @NotNull
    @Size(min = 16, max = 16)
    @Column(name = "cd_pan", nullable = false, length = 16)
    private String pan;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_card_situation", nullable=false)
    private CardSituation cardSituation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_customer", nullable=false)
    private Customer customer;
}