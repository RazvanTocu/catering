package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Credentials;
import com.example.demo.service.CredentialsService;

@Component
public class CredentialsValidator implements Validator {
	
	@Autowired private CredentialsService credentialsService;

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Credentials.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		if(this.credentialsService.alreadyExist((Credentials)target)) {
			errors.reject("credentials.duplicato");
		}

	}

}
