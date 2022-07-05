package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long> {
	
	List<Chef> findTop3ByOrderByIdDesc();

	boolean existsByNameAndSurname(String name, String surname);

}
