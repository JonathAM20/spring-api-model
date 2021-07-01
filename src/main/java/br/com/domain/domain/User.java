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
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @NotNull
    @Size(min = 6, max = 50)
    @Column(name = "ds_name", nullable = false, length = 50)
    private String username;

    @NotNull
    @Size(min = 6, max = 50)
    @Column(name = "ds_password", nullable = false, length = 50)
    private String password;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_user_profile", nullable=false)
    private UserProfile userProfile;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_user_situation", nullable=false)
    private UserSituation userSituation;
}