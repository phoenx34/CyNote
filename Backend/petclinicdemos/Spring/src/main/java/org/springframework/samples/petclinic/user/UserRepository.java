package org.springframework.samples.petclinic.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */
public interface UserRepository extends JpaRepository<User, String>{

}
