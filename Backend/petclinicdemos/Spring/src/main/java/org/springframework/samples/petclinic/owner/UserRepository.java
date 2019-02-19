package org.springframework.samples.petclinic.owner;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */
public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByUsername(String userName);

}
