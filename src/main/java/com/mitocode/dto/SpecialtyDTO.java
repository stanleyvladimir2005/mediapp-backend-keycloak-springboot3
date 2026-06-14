package com.mitocode.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SpecialtyDTO {

    @EqualsAndHashCode.Include
    private Integer idSpecialty;

    @NotNull
    private String specialtyName;

    @NotNull
    private String description;

    @NotNull
    private Boolean status;
}