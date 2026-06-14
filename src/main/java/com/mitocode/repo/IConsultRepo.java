package com.mitocode.repo;

import com.mitocode.model.Consult;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IConsultRepo extends IGenericRepo <Consult, Integer> {
	
 @Query("from Consult c where c.patient.dui =:dui or LOWER(c.patient.firstName) like %:fullname% or LOWER(c.patient.lastName) like %:fullname%")
 List<Consult> search(@Param("dui")String dui, @Param("fullname") String fullname);

 @Query("from Consult c where c.consultDate between :date1 and :date2")
 List<Consult> searchByDates(@Param("date1") LocalDateTime date1, @Param("date2") LocalDateTime date2);

 @Query(value = "select * from fn_list()", nativeQuery = true)
 List<Object[]> callProcedureOrFunction();
}