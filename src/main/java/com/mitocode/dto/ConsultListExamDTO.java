package com.mitocode.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ConsultListExamDTO {
	@NotNull
	private ConsultDTO consult;

	@NotNull
	private List<ExamDTO> listExam;
}