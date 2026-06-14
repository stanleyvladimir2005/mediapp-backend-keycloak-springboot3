package com.mitocode.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "consult_detail")
public class ConsultDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDetail;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "id_consult", nullable = false)
	private Consult consult;

	@Column(name = "diagnosis", nullable = false, length = 70)
	private String diagnosis;

	@Column(name = "treatment", nullable = false, length = 300)
	private String treatment;
}