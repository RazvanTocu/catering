package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
