package org.springframework.samples.petclinic.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */
public interface UserRepository extends CrudRepository<User, Integer>{
	User save(User persisted);

	
	User findByUsername(String username);

}
