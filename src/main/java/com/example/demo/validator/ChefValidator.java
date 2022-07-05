package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Chef;
import com.example.demo.service.ChefService;

@Component
public class ChefValidator implements Validator {

	@Autowired private ChefService chefService;

	@Override
	public boolean supports(Class<?> clazz) {
		Chef.class.equals(clazz);
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		if(this.chefService.alreadyExist((Chef)target))
			errors.reject("chef.duplicato");

	}

}
