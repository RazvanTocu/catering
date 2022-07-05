package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;

@Controller
public class CateringController {
	
	@Autowired private BuffetService buffetService;
	@Autowired private ChefService   chefService;	
	
	@GetMapping("/")
	public String homepage(Model model) {
		model.addAttribute("chefs", this.chefService.lastInsertedChef());
		model.addAttribute("buffets", this.buffetService.lastInsertedBuffet());
		return "index";
	}

}
