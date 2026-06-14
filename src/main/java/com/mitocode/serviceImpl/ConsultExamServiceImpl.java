package com.mitocode.serviceImpl;

import com.mitocode.model.ConsultExam;
import com.mitocode.repo.IConsultExamRepo;
import com.mitocode.service.IConsultExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultExamServiceImpl implements IConsultExamService {

	@Autowired
	private IConsultExamRepo repo;
	
	@Override
	public List<ConsultExam> listExamByConsult(Integer idConsult) {
		return repo.listExamByConsult(idConsult);
	}
}