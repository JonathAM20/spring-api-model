package br.com.domain.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 11)
    @Column(name = "cd_personal", nullable = false, length = 11)
    private String personalCode;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "ds_first_name", nullable = false, length = 50)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "ds_last_name", nullable = false, length = 50)
    private String lastName;

    @OneToMany(mappedBy="customer", fetch = FetchType.EAGER)
    private List<Card> cards;
}