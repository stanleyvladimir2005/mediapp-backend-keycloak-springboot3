package com.mitocode.repo;

import com.mitocode.model.Specialty;
import org.springframework.stereotype.Repository;

@Repository
public interface ISpecialtyRepo extends IGenericRepo<Specialty, Integer> {
}