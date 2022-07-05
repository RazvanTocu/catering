package com.example.demo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Credentials;
import com.example.demo.repository.CredentialsRepository;

@Service
public class CredentialsService {
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Credentials getCredentials(Long id) {
		Optional<Credentials> result = this.credentialsRepository.findById(id);
		return result.orElse(null);
	}
	
	public Credentials getCredentials(String username) {
		Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
		return result.orElse(null);
	}
	
	@Transactional
	public Credentials saveCredentials(Credentials credentials) {
		if (credentials.getRuolo() == null) {
			credentials.setRuolo(Credentials.GENERIC_USER_ROLE);
		}
		credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
		return credentialsRepository.save(credentials);
	}

	public boolean alreadyExist(Credentials credentials) {
		// TODO Auto-generated method stub
		return this.credentialsRepository.existsByUsername(credentials.getUsername());
	}
}
