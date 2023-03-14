package com.mitocode.controller;

import com.mitocode.dto.*;
import com.mitocode.model.Consult;
import com.mitocode.model.Exam;
import com.mitocode.model.MediaFile;
import com.mitocode.service.IConsultService;
import com.mitocode.service.IMediaFileService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/consults")
public class ConsultController {
	
	@Autowired
	private IConsultService service;
	
	@Autowired
	private IMediaFileService mediaFileService;

	@Autowired
	private ModelMapper mapper;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ConsultDTO>> findAll() {
		List<ConsultDTO> consults =  service.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
		return new ResponseEntity<>(consults, OK);
	}
		
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> save(@Valid @RequestBody ConsultListExamDTO dto) {
		Consult c = convertToEntity(dto.getConsult());
		List<Exam> exams = mapper.map(dto.getListExam(), new TypeToken<List<Exam>>() {}.getType());
		Consult obj = service.saveTransactional(c, exams);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult()).toUri();
		return ResponseEntity.created(location).build();			
	}

	@PutMapping("/{id}")
	public ResponseEntity<ConsultDTO> update(@PathVariable("id") Integer id,@Valid @RequestBody ConsultDTO consultDTO) {
		consultDTO.setIdConsult(id);
		Consult cons = service.update(convertToEntity(consultDTO),id);
		return new ResponseEntity<>(convertToDto(cons),OK);
	}

	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return new ResponseEntity<>(OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ConsultDTO> findById(@PathVariable("id") Integer id) {
		Consult consult = service.findById(id);
		return new ResponseEntity<>(this.convertToDto(consult), OK);
	}
	
	@GetMapping(value="/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<ConsultDTO>> listPageable(Pageable pageable) {
		Page<ConsultDTO> consultDTO  = service.listPageable(pageable).map(this::convertToDto);
		return new ResponseEntity<>(consultDTO, OK);
	}
	
	@GetMapping(value = "/hateoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ConsultHateoasDTO> listHateoas() {
		List<Consult> consults;
		List<ConsultHateoasDTO> consultasDTO = new ArrayList<>();
		consults = service.findAll();

		for (Consult c : consults) {
			ConsultHateoasDTO d = new ConsultHateoasDTO();
			d.setIdConsult(c.getIdConsult());
			d.setMedic(c.getMedic());
			d.setPatient(c.getPatient());

			WebMvcLinkBuilder linkTo = linkTo(methodOn(ConsultController.class).findById((c.getIdConsult())));
			d.add(linkTo.withSelfRel());

			WebMvcLinkBuilder linkTo1 = linkTo(methodOn(PatientController.class).findById((c.getPatient().getIdPatient())));
			d.add(linkTo1.withSelfRel());

			WebMvcLinkBuilder linkTo2 = linkTo(methodOn(MedicController.class).findById((c.getMedic().getIdMedic())));
			d.add(linkTo2.withSelfRel());
			consultasDTO.add(d);
		}
		return consultasDTO;
	}

	@PostMapping("/search/others")
	public ResponseEntity<List<ConsultDTO>> searchByOthers(@RequestBody FilterConsultDTO filterDTO){
		List<Consult> consults = service.search(filterDTO.getDui(), filterDTO.getFullname());
		List<ConsultDTO> consultsDTO = mapper.map(consults, new TypeToken<List<ConsultDTO>>() {}.getType());
		return new ResponseEntity<>(consultsDTO, OK);
	}

	@GetMapping("/search/date")
	public ResponseEntity<List<ConsultDTO>> searchByDates(@RequestParam(value = "date1") String date1, @RequestParam(value = "date2") String date2){
		List<Consult> consults = service.searchByDates(LocalDateTime.parse(date1), LocalDateTime.parse(date2));
		List<ConsultDTO> consultsDTO = mapper.map(consults, new TypeToken<List<ConsultDTO>>() {}.getType());
		return new ResponseEntity<>(consultsDTO, OK);
	}
	
	@GetMapping(value = "/callProcedure")
	public ResponseEntity<List<ConsultProductDTO>> listProducts() {
		List<ConsultProductDTO> consults  = service.callProcedureOrFunction();
		return new ResponseEntity<>(consults, OK);
	}
	
	@GetMapping(value = "/generateReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> generateReport() throws Exception {
		byte[] data = service.generateReport();
		return new ResponseEntity<>(data, OK);
	}
	
	@PostMapping(value = "/saveFile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<Void> saveFile(@RequestParam("file") MultipartFile file) throws IOException{
		MediaFile mf = new MediaFile();
		mf.setFileType(file.getContentType());
		mf.setFileName(file.getName());
		mf.setValue(file.getBytes());
		mediaFileService.save(mf);
		return new ResponseEntity<>(OK);
	}
	
	@GetMapping(value = "/readFile/{idFile}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> readFile(@PathVariable("idFile") Integer idFile) {
		byte[] arr = mediaFileService.findById(idFile).getValue();
		return new ResponseEntity<>(arr, OK);
	}

	private ConsultDTO convertToDto(Consult obj){
		return mapper.map(obj, ConsultDTO.class);
	}

	private Consult convertToEntity(ConsultDTO dto){
		return mapper.map(dto, Consult.class);
	}
}