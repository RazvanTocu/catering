package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;

public interface BuffetRepository extends CrudRepository<Buffet, Long> {

	boolean existsByName(String name);
	
	List<Buffet> findTop3ByOrderByIdDesc();
	
	Buffet findByNameAndChef(String name, Chef chef);

}
