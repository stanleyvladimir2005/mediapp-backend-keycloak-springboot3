package com.mitocode.serviceImpl;

import com.mitocode.model.Patient;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IPatientRepo;
import com.mitocode.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl extends CRUDImpl<Patient, Integer> implements IPatientService {

	@Autowired
	private IPatientRepo repo;

	@Override
	protected IGenericRepo<Patient, Integer> getRepo() {
		return repo;
	}
}