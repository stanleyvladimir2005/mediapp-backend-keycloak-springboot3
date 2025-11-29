package com.mitocode.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="medic")
public class Medic {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMedic;

	@Size(min = 3, message = "{first_name.size}")
	@Column(name = "first_name", nullable = false, length = 70)
	private String firstName;

	@Size(min = 3, message = "{last_name.size}")
	@Column(name = "last_name", nullable = false, length = 70)
	private String lastName;

	@Size(min = 9, max = 9, message = "{dui.size}")
	@Column(name = "dui", nullable = false, length = 9)
	private String dui;

	@Size(min = 8, max = 8, message = "{phone.size}")
	@Column(name = "phone", nullable = false, length = 8)
	private String phone;
	
	@Email
	@Size(message = "{email.size}")
	@Column(name = "email", length = 150)
	private String email;

	@Column(name = "photoUrl")
	private String photoUrl;
	
	@Column(name = "status")
	private Boolean status;
}