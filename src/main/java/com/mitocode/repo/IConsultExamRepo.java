package com.mitocode.repo;

import com.mitocode.model.ConsultExam;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IConsultExamRepo extends IGenericRepo<ConsultExam, Integer> {

	@Modifying
	@Query(value = "INSERT INTO consult_exam(id_consult, id_exam) VALUES (:idConsult, :idExam)", nativeQuery = true)
	Integer register(@Param("idConsult") Integer idConsult,@Param("idExam") Integer idExam);
	
	@Query("from ConsultExam ce where ce.consult.idConsult = :idConsult")
	List<ConsultExam> listExamByConsult(@Param("idConsult") Integer idConsult);
}