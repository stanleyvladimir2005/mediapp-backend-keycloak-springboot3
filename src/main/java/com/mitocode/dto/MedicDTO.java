package com.mitocode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MedicDTO {

    @EqualsAndHashCode.Include
     private Integer idMedic;

    @NotNull
    @Size(min = 3, max = 70, message = "{first_name.size}")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 70, message = "{last_name.size}")
    private String lastName;

    @NotNull
    @Pattern(regexp = "[0-9]+")
    @Size(min = 9, max = 9, message = "{dui.size}")
    private String dui;

    @NotNull
    @Pattern(regexp = "[0-9]+")
    @Size(min = 8, max = 8, message = "{phone.size}")
    private String phone;

    @Email
    @NotNull
    @Size(message = "{email.size}", max = 150)
    private String email;

    @NotNull
    private String photoUrl;

    @NotNull
    private Boolean status;
}