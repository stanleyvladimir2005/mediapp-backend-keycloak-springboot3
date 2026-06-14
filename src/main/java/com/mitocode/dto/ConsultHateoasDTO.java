package com.mitocode.dto;

import com.mitocode.model.Medic;
import com.mitocode.model.Patient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.EntityModel;

//Clase auxiliar para crear el HATEOAS nivel 3 a la consulta
@EqualsAndHashCode(callSuper = true)
@Data
public class ConsultHateoasDTO extends EntityModel<Object> {
	private Integer idConsult;
	private Medic medic;
	private Patient patient;
}