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
	UserController userController;
	
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
	/*public boolean emailAlreadyExisted(String emailAddress) throws IllegalArgumentException 
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
	 *
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
	}*/
	
	/**
	 * Find the userID from the UserScreen name
	 * @param userName The screen name provided to find the user 
	 * @return The userID in string
	 * @throws IllegalArgumentException 
	 */
	public User findUserFromUsername(String userName)throws IllegalArgumentException
	{
		if(userName == null || userName.trim().length()==0)
    		throw new IllegalArgumentException("The input email address is not valid");
        List<User> results = this.getUsers();       // list of users 
        for(User user : results)
    	{
    		if(user.getScreenname().equals(userName))
    		{
    			return user;
    		}
    	}
        return null; 
	}
	
	
	
	
	public List<User> getUsers() {
        List<User> results = (List<User>) userRepository.findAll();
        return results;
    }

	
	
	
	
	
	
	
	
}
