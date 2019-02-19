package org.springframework.samples.petclinic.user;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserApplication {
	@Autowired
	UserRepository userRepository;
	
	public User create(User newUser) {
		return userRepository.save(newUser);
	}
}
