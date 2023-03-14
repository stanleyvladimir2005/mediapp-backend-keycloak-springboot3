package com.mitocode.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "specialty")
@SQLDelete(sql = "UPDATE specialty SET status = false WHERE id_specialty = ?")
@Where(clause = "status = true")
public class Specialty {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idSpecialty;
	
	@Column(name = "specialty_name", nullable = false, length = 50)
	@Size(min=3, message ="{speciality_name.size}")
	private String specialtyName;

	@Column(name = "description", nullable = false, length = 150)
	@Size(min=3, message ="{speciality_name.size}")
	private String description;

	@Column(name = "status")
	private Boolean status;
}