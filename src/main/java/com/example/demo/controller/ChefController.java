package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Chef;
import com.example.demo.service.ChefService;
import com.example.demo.validator.ChefValidator;

@Controller
@RequestMapping("/chef")
public class ChefController {
	
	@Autowired ChefService chefService;

	@Autowired private ChefValidator chefValidator;
	
	private static final String CHEF_DIR = "chef/";

	@GetMapping("/all")
	public String getChefList(Model model) {
		List<Chef> chefs = chefService.findAll();
		model.addAttribute("chefs",chefs);
		return CHEF_DIR + "ChefList.html";
	}	
	
	// mostra uno specifico chef selezionato tramite id
	@GetMapping("/{id}")
	public String getChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", this.chefService.findById(id));
		return CHEF_DIR + "ChefProfile";
	}	

	
	// mostra la form per l'aggiunta di un nuovo chef
	@GetMapping("/add/form")
	public String getAddForm(Model model) {
		model.addAttribute("chef", new Chef());
		return CHEF_DIR + "chefAdd";
	}

	// aggiunge al db un nuovo chef
	@PostMapping("/add")
	public String addchef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult ,Model model) {
		this.chefValidator.validate(chef, bindingResult);
		if(!bindingResult.hasErrors()) {
			this.chefService.save(chef);
			return this.getChef(chef.getId(), model); //mostro il riepilogo dello chef inserito			
		}
		return CHEF_DIR + "chefAdd";

	}
	
	// elimina dal db un chef selezionato tramite id
	@GetMapping("/delete/{id}")
	public String deleteChef(@PathVariable("id") Long id,Model model) {
		this.chefService.deleteById(id);
		//model.addAttribute("chefs", this.chefService.findAll());
		return "redirect:/chef/all";
	}
	
	// mostra la form per l'edit di un chef selezionato tramite id
	@GetMapping("/edit/{id}")
	public String getEditForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", this.chefService.findById(id));
		return CHEF_DIR + "ChefEdit";
	}
	
	
	// edita un chef nel db
	@PostMapping("/edit/{id}")
	public String editChef(@Valid @ModelAttribute("chef") Chef chef, BindingResult bindingResult, Model model) {
		this.chefValidator.validate(chef, bindingResult);	
		if(!bindingResult.hasErrors()) {		
			this.chefService.update(chef);
			return "redirect:/chef/all";
		}
		return CHEF_DIR + "ChefEdit";
	}
	
}
