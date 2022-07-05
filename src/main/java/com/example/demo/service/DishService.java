package com.example.demo.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Dish;
import com.example.demo.model.Ingredient;
import com.example.demo.repository.DishRepository;

@Service
public class DishService {
	
	@Autowired private DishRepository dishRepository;

	public List<Dish> findAll(){
		List<Dish> dishs = new LinkedList<Dish>();
		for(Dish b : this.dishRepository.findAll())
			dishs.add(b);
		return dishs;
	}

	public Dish findById(Long id) {
		return this.dishRepository.findById(id).get();
	}
	
	@Transactional
	public void save(Dish b) {
		this.dishRepository.save(b);
	}
	
	@Transactional
	public void delete(Dish b) {
		this.dishRepository.delete(b);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.dishRepository.deleteById(id);
	}


	public void update(Dish dish) {
		// TODO Auto-generated method stub
		Dish foo = this.dishRepository.findById(dish.getId()).get();
		foo.setName(dish.getName());
		foo.setDescription(dish.getDescription());
		this.dishRepository.save(foo);
		
	}

	public void addIngredient(Ingredient ingredient, Long idDish) {
		// TODO Auto-generated method stub
		Dish dish = this.dishRepository.findById(idDish).get();
		dish.getIngredients().add(ingredient);
		this.save(dish);
	}	
	
}
