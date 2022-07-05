package com.example.demo.service;

import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.repository.ChefRepository;

@Service
public class ChefService {

	@Autowired ChefRepository chefRepository;
	
	public List<Chef> findAll(){
		List<Chef> chefs = new LinkedList<Chef>();
		for(Chef b : this.chefRepository.findAll())
			chefs.add(b);
		return chefs;
	}
	
	
	public Chef findById(Long id) {
		return this.chefRepository.findById(id).get();
	}
	
	@Transactional
	public void save(Chef b) {
		this.chefRepository.save(b);
	}
	
	@Transactional
	public void delete(Chef b) {
		this.chefRepository.delete(b);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.chefRepository.deleteById(id);
	}

	@Transactional
	public void update(Chef chef) {
		// TODO Auto-generated method stub
		Chef foo = this.chefRepository.findById(chef.getId()).get();
		foo.setName(chef.getName());
		foo.setSurname(chef.getSurname());
		foo.setNationality(chef.getNationality());
		this.chefRepository.save(foo);
	}

	@Transactional
	public void addBuffet(Long idChef, Buffet buffet) {
		// TODO Auto-generated method stub
		Chef chef = this.chefRepository.findById(idChef).get();
		chef.getBuffets().add(buffet);
		buffet.setChef(chef);
		this.chefRepository.save(chef);
	}
	
	public List<Chef> lastInsertedChef(){
		return this.chefRepository.findTop3ByOrderByIdDesc();
	}


	public boolean alreadyExist(Chef chef) {
		// TODO Auto-generated method stub
		return this.chefRepository.existsByNameAndSurname(chef.getName(), chef.getSurname());
	}
}
