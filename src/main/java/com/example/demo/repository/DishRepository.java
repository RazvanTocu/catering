package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Dish;

public interface DishRepository extends CrudRepository<Dish, Long> {

}
