package br.com.domain.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "tb_card_situation")
public class CardSituation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card_situation")
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "ds_situation", nullable = false, length = 50)
    private String situation;
}