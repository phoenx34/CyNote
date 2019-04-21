package org.springframework.samples.petclinic.user;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * The user repository that connects with the datbase 
 * @author Shen Chen
 * @author Marc Issac
 */
public interface UserRepository extends CrudRepository<User, Integer>{
	
	/**
	 * Save a user in the database 
	 */
	User save(User persisted);
	
	
	
	/**
	 * Find the user by given user ID
	 */
	Optional<User> findById(Integer UID);
	
	
	
	/**
	 * Return all the user on the database 
	 */
	List<User> findAll();
	
}
