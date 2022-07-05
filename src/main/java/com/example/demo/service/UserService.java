package com.example.demo.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	
	public User getUser(Long id) {
		
		/* Optional in java e' un container di object che possono essere
		 * anche null... fornisce metodi per gestire il caso null in modo
		 * semplice e rapido...												
		 * */
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
	}
	
	@Transactional
	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

}
