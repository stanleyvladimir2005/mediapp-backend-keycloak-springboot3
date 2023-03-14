package com.mitocode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Data
@Entity
@IdClass(ConsultExamPK.class)
public class ConsultExam {

	@Id
	private Exam exam;

	@Id
	private Consult consult;
}