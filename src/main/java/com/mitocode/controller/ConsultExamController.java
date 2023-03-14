package com.mitocode.controller;

import com.mitocode.dto.ConsultExamDTO;
import com.mitocode.model.ConsultExam;
import com.mitocode.service.IConsultExamService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/consult-exam")
public class ConsultExamController {

	@Autowired
	private IConsultExamService service;

	@Autowired
	private ModelMapper mapper;
	
	@GetMapping(value = "/{idConsult}")
	public ResponseEntity<List<ConsultExamDTO>> getConsultsById(@PathVariable("idConsult") Integer idConsult) {
		List<ConsultExam> consultaExamen = service.listExamByConsult(idConsult);
		List<ConsultExamDTO> lstDTO = mapper.map(consultaExamen, new TypeToken<List<ConsultExamDTO>>() {}.getType());
		return new ResponseEntity<>(lstDTO, OK);
	}
}