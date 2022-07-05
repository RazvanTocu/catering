package com.example.demo.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Ingredient;
import com.example.demo.repository.IngredientRepository;

@Service
public class IngredientService {

	@Autowired private IngredientRepository ingredientRepository;
	
	public List<Ingredient> findAll(){
		List<Ingredient> ingredients = new LinkedList<Ingredient>();
		for(Ingredient i : this.ingredientRepository.findAll())
			ingredients.add(i);
		return ingredients;
	}

	public Ingredient findById(Long id) {
		return this.ingredientRepository.findById(id).get();
	}
	
	@Transactional
	public void save(Ingredient i) {
		this.ingredientRepository.save(i);
	}
	
	@Transactional
	public void delete(Ingredient i) {
		this.ingredientRepository.delete(i);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.ingredientRepository.deleteById(id);
	}

	@Transactional
	public void update(Ingredient ingredient) {
		// TODO Auto-generated method stub
		Ingredient foo = this.ingredientRepository.findById(ingredient.getId()).get();
		foo.setName(ingredient.getName());
		foo.setDescription(ingredient.getDescription());	
		foo.setOrigin(ingredient.getOrigin());	
		this.ingredientRepository.save(foo);
	}
}
