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
@RequestMapping("/v1/exams")
public class ExamController {
	
	@Autowired
	private IExamService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ExamDTO>> findAll() {
		var exam = service.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
		return new ResponseEntity<>(exam, OK);
	}
		
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save (@Valid @RequestBody ExamDTO examDTO) {
		var exa = service.save(convertToEntity(examDTO));
		var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(exa.getIdExam()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<ExamDTO> update(@PathVariable("id") Integer id, @Valid @RequestBody ExamDTO examDTO) {
        examDTO.setIdExam(id);
		var exa = service.update(convertToEntity(examDTO), id);
		return new ResponseEntity<>(convertToDto(exa),OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExamDTO> findById(@PathVariable("id") Integer id) {
		var exam = service.findById(id);
		return new ResponseEntity<>(this.convertToDto(exam),OK);
	}
	
	@GetMapping(value="/pageableExam", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ExamDTO>> listPageable(Pageable pageable) {
		var examDTO = service.listPageable(pageable).map(this::convertToDto);
		return new ResponseEntity<>(examDTO, OK);
	}

	@GetMapping("/hateoas/{id}")
	public EntityModel<ExamDTO> findByIdHateoas(@PathVariable("id") Integer id) {
		var exa = service.findById(id);
		var dtoResponse = convertToDto(exa);
		var resource = EntityModel.of(dtoResponse);
		var link1 = linkTo(methodOn(this.getClass()).findById(id));
		var link2 = linkTo(methodOn(this.getClass()).findAll());
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