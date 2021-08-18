package br.com.domain.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    @ApiModelProperty(
            value = "Identification of the Card",
            name = "id",
            dataType = "Long",
            example = "1")
    private Long id;

    @NotNull
    @Size(min = 16, max = 16)
    @Column(name = "cd_pan", nullable = false, length = 16)
    @ApiModelProperty(
            value = "Specific code card",
            name = "id",
            dataType = "Long",
            example = "1")
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