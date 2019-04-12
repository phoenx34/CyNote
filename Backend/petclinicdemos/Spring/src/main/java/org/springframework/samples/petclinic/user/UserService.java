package org.springframework.samples.petclinic.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

	 @Autowired          
	 UserRepository usersRepository;
	 
	 
	 public User findUserById(Integer UID) 
	 {
		 return usersRepository.getUserById(UID);
	 }
	 
	 
	 public List<User> findAllUsers()
	 {
		 return usersRepository.findAll();
	 }
	
}
