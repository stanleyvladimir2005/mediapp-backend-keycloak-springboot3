package com.mitocode.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
public class ConsultExamPK implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "id_exam", nullable = false)
	private Exam exam;

	@EqualsAndHashCode.Include
	@ManyToOne
	@JoinColumn(name = "id_consult", nullable = false)
	private Consult consult;
}