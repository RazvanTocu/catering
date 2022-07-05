package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Ingredient;
import com.example.demo.service.DishService;
import com.example.demo.service.IngredientService;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {
	
	public static final String INGREDIENT_DIR = "ingredient/";

	@Autowired private IngredientService ingredientService;
	
	@Autowired private DishService dishService;
	
	private static final String DISH_DIR = "/ingredient/";

	@GetMapping("/all")
	public String getIngredientList(Model model) {
		List<Ingredient> ingredients = ingredientService.findAll();
		model.addAttribute("ingredients",ingredients);
		return DISH_DIR + "IngredientList.html";
	}	
	
	// mostra uno specifico ingredient selezionato tramite id
	@GetMapping("/{id}")
	public String getIngredient(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ingredient", this.ingredientService.findById(id));
		return "index";
	}	

	
	// mostra la form per l'aggiunta di un nuovo ingredient per un relativo dish
	@GetMapping("/add/{idDish}/{idBuffet}")
	public String getAddForm(@PathVariable("idDish") Long idDish, @PathVariable("idBuffet") Long idBuffet
										,Model model) {
		model.addAttribute("ingredient", new Ingredient());
		model.addAttribute("dish", this.dishService.findById(idDish));
		model.addAttribute("idBuffet", idBuffet);
		return DISH_DIR + "ingredientAdd";
	}

	// aggiunge al db un nuovo ingredient per un relativo dish
	@PostMapping("/add/{idDish}/{idBuffet}")
	public String addIngredient(@ModelAttribute("ingredient") Ingredient ingredient, @PathVariable("idDish") Long idDish, 
												@PathVariable("idBuffet") Long idBuffet,Model model) {
		this.dishService.addIngredient(ingredient, idDish);
		return "redirect:/buffet/" + idBuffet; // voglio ritornare alla pagina che mostra il dish
		// redirect mi permette di ripassare per il controller
	}
	
	
	// elimina dal db un ingredient selezionato tramite id
	@GetMapping("/delete/{id}/{buffetId}")
	public String deleteIngredient(@PathVariable("id") Long id,
							@PathVariable("buffetId") Long buffetId, Model model) {
		this.ingredientService.deleteById(id);
		return "redirect:/buffet/" + buffetId;
	}
	
	// mostra la form per l'edit di un ingredient selezionato tramite id
	@GetMapping("/edit/{id}/{buffetId}")
	public String getEditForm(@PathVariable("id") Long id,
										@PathVariable("buffetId") Long buffetId, Model model) {
		model.addAttribute("ingredient", this.ingredientService.findById(id));
		model.addAttribute("buffetId", buffetId);
		return DISH_DIR + "IngredientEdit";
	}
	
	
	// edita un ingredient nel db
	@PostMapping("/edit/{id}/{buffetId}")
	public String editIngredient(@ModelAttribute("ingredient") Ingredient ingredient, 
								@PathVariable("buffetId") Long buffetId, Model model) {
		
		this.ingredientService.update(ingredient);
		return "redirect:/buffet/" + buffetId;
	}	
	
}
