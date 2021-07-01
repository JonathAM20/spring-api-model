package br.com.domain.domain;

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
@Table(name = "tb_user_situation")
public class UserSituation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_situation")
    private Long id;

    @NotNull
    @Size(min = 5, max = 50)
    @Column(name = "ds_situation", nullable = false, length = 50)
    private String situation;
}