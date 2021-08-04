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
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    @ApiModelProperty(
            value = "Identification of the Customer",
            name = "id",
            dataType = "Long",
            example = "1")
    private Long id;

    @NotNull
    @Size(min = 2, max = 11)
    @Column(name = "cd_personal", nullable = false, length = 11)
    @ApiModelProperty(
            value = "Personal code of the Customer",
            name = "personalCode",
            dataType = "String",
            example = "12345678910")
    private String personalCode;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "ds_first_name", nullable = false, length = 50)
    @ApiModelProperty(
            value = "First name of the Customer",
            name = "firstName",
            dataType = "String",
            example = "First")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "ds_last_name", nullable = false, length = 50)
    @ApiModelProperty(
            value = "Last name of the Customer",
            name = "lastName",
            dataType = "String",
            example = "Last")
    private String lastName;

    public Customer(Long id) {
        this.id = id;
    }
}