package com.mitocode.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PatientDTO {

    @EqualsAndHashCode.Include
    private Integer idPatient;

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
    @Size(min = 3, max = 150, message = "{address.size}")
    private String address;

    @NotNull
    @Pattern(regexp = "[0-9]+")
    @Size(min = 8, max = 8, message = "{phone.size}")
    private String phone;

    @Email
    @NotNull
    @Size(max = 150, message = "{email.size}")
    private String email;

    @NotNull
    private Boolean status;
}