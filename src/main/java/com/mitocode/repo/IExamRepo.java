package com.mitocode.repo;

import com.mitocode.model.Exam;
import org.springframework.stereotype.Repository;

@Repository
public interface IExamRepo extends IGenericRepo<Exam, Integer> {
}