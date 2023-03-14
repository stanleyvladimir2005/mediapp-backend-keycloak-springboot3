package com.mitocode.repo;

import com.mitocode.model.Medic;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicRepo extends IGenericRepo<Medic, Integer> {
}