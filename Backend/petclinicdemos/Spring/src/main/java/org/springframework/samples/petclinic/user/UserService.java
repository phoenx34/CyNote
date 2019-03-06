package org.springframework.samples.petclinic.user;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	/**
	 * Create a new user and store in the data base
	 * @param newUser the user object that's been passed in to the method
	 * @return the user been saved 
	 */
	public User create(User newUser) {
		return userRepository.save(newUser);
	}
	
	
	/**
	 * This method is to check if the email already exist in the database
	 * @param emailAddress The given email address 
	 * @return Whether if the email already exist in the database
	 * @throws IllegalArgumentException When the input is not even valid
	 */
	public boolean emailAlreadyExisted(String emailAddress) throws IllegalArgumentException 
	{
		if(emailAddress == null || emailAddress.trim().length()==0)
    		throw new IllegalArgumentException("The input email address is not valid"); 
        List<User> results = (List<User>) userRepository.findAll();       // list of users 
        for(User user : results)
    	{
    		if(user.getEmail().equals(emailAddress))
    		{
    			return true;
    		}
    	}
        return false;  
	}
	
	
	
	/**
	 * This method is to check if the username already exist in the database
	 * @param userName This is given as a string at the moment
	 * @return Whether if the username already exist in the database
	 * @throws IllegalArgumentException When the input is not even valid
	 */
	public boolean usernamelAlreadyExisted(String userName)throws IllegalArgumentException
	{
		if(userName == null || userName.trim().length()==0)
    		throw new IllegalArgumentException("The input email address is not valid");
        List<User> results = (List<User>) userRepository.findAll();       // list of users 
        for(User user : results)
    	{
    		if(user.getEmail().equals(userName))
    		{
    			return true;
    		}
    	}
        return false; 
	}

	

	// ?????????? The method might still not work yet 
	/**
	 * Finding user by the user name
	 * @param username The given input string 
	 * @return return the user with given username
	 */
	public User findUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}
	
	
	
	
	
	
	
	
	
}
