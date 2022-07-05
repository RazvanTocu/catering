package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.Credentials;
import com.example.demo.model.User;
import com.example.demo.service.CredentialsService;
import com.example.demo.validator.CredentialsValidator;

@Controller
public class AuthenticationController {

	@Autowired private CredentialsService credentialsService;
	@Autowired private CredentialsValidator credentialsValidator;
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "auth/registerUser";
	}
	

	@PostMapping(value= "/register")
	public String registerUser(@Valid @ModelAttribute("user") User user,
								BindingResult userBindingResult, 
							    @Valid @ModelAttribute("credentials") Credentials credentials,
							    BindingResult credentialsBindingResult,
							    Model model) {
		
        // validazione user e credenziali
        //this.userValidator.validate(user, userBindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

		if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors() ) {
			credentials.setUser(user);
			credentialsService.saveCredentials(credentials);
			return "redirect:/";
		}
		model.addAttribute("user", user);
		model.addAttribute("credentials", credentials);		
		return "auth/registerUser";	
	}
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String showLoginForm(Model model) {
		return "auth/loginForm";
	}

	
	@GetMapping("/default")
	public String defaultAfterLogin(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
//		if(credentials.getRuolo().equals(Credentials.ADMIN_ROLE)) {
//			return "redirect:/";
//		}
		return "redirect:/";

	}
	
	@GetMapping("/logout")
	public String logout(Model model) {
		return "redirect:/";
	}
	

	
}
