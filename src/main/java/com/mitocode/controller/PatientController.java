package com.mitocode.controller;

import com.mitocode.dto.PatientDTO;
import com.mitocode.model.Patient;
import com.mitocode.service.IPatientService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/patients")
public class PatientController {
	
	@Autowired
	private IPatientService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PatientDTO>> findAll() {
		var patients = service.findAll().stream().map(this::convertToDto).collect(Collectors.toList()); //p -> convertToDto(p))
		return new ResponseEntity<>(patients, OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(@Valid @RequestBody PatientDTO patientDTO) {
		var pac = service.save(convertToEntity(patientDTO));
		var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pac.getIdPatient()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<PatientDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody PatientDTO patientDTO) {
		patientDTO.setIdPatient(id);
		var pac = service.update(convertToEntity(patientDTO), id);
		return new ResponseEntity<>(convertToDto(pac),OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PatientDTO> findById(@PathVariable("id") Integer id) {
		var patient = service.findById(id);
		return new ResponseEntity<>(this.convertToDto(patient), OK);
	}
	
	@GetMapping(value="/pageablePatient", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<PatientDTO>> listPageable(Pageable pageable) {
		var patientsDTO = service.listPageable(pageable).map(this::convertToDto);
		return new ResponseEntity<>(patientsDTO, OK);
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<PatientDTO> findByIdHateoas(@PathVariable("id") Integer id) {
		var pac = service.findById(id);
		var dtoResponse = convertToDto(pac);
		var resource = EntityModel.of(dtoResponse);
		var link1 = linkTo(methodOn(this.getClass()).findById(id));
		var link2 = linkTo(methodOn(this.getClass()).findAll());
		resource.add(link1.withRel("patient-info1"));
		resource.add(link2.withRel("patient-full"));
		return resource;
	}

	private PatientDTO convertToDto(Patient obj){
		return mapper.map(obj, PatientDTO.class);
	}

	private Patient convertToEntity(PatientDTO dto){
		return mapper.map(dto, Patient.class);
	}
}