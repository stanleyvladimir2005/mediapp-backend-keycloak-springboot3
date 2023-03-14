package com.mitocode.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICRUD<T, ID> {

	T save(T t);

	T update(T t, ID id);

	List<T> findAll();
	
	T findById(ID id);

	void delete(ID id);
	
	Page<T> listPageable(Pageable pageable);
}