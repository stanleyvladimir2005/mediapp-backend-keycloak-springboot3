package com.mitocode.controller;

import com.mitocode.dto.ExamDTO;
import com.mitocode.model.Exam;
import com.mitocode.service.IExamService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/exams")
public class ExamController {
	
	@Autowired
	private IExamService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ExamDTO>> findAll() {
		List<ExamDTO> exam = service.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
		return new ResponseEntity<>(exam, OK);
	}
		
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save (@Valid @RequestBody ExamDTO examDTO) {
		Exam exa = service.save(convertToEntity(examDTO));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(exa.getIdExam()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<ExamDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody ExamDTO examDTO) {
        examDTO.setIdExam(id);
		Exam exa = service.update(convertToEntity(examDTO), id);
		return new ResponseEntity<>(convertToDto(exa),OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExamDTO> findById(@PathVariable("id") Integer id) {
		Exam exam = service.findById(id);
		return new ResponseEntity<>(this.convertToDto(exam),OK);
	}
	
	@GetMapping(value="/pageableExam", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ExamDTO>> listPageable(Pageable pageable) {
		Page<ExamDTO> examDTO = service.listPageable(pageable).map(this::convertToDto);
		return new ResponseEntity<>(examDTO, OK);
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<ExamDTO> findByIdHateoas(@PathVariable("id") Integer id) {
		ExamDTO dtoResponse;
		Exam exa = service.findById(id);
		dtoResponse = convertToDto(exa);
		EntityModel<ExamDTO> resource = EntityModel.of(dtoResponse);
		WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
		WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());
		resource.add(link1.withRel("exam-info1"));
		resource.add(link2.withRel("exam-full"));
		return resource;
	}

	private ExamDTO convertToDto(Exam obj){
		return mapper.map(obj, ExamDTO.class);
	}

	private Exam convertToEntity(ExamDTO dto){
		return mapper.map(dto, Exam.class);
	}
}