package com.mitocode.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ExamDTO {

    @EqualsAndHashCode.Include
    private Integer idExam;

    @NotNull
    @Size(min=3, max = 50, message="{examen_name.size}")
    private String examName;


    @NotNull
    @Size(min=3, max = 150, message="{exam_description.size}")
    private String description;

    @NotNull
    private Boolean status;
}
