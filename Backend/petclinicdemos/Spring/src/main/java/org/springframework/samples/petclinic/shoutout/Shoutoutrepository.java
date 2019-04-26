package org.springframework.samples.petclinic.shoutout;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * Shoutoutrepository Connects with the databse 
 * @author Shen Chen
 * @author Marc Issac
 */
public interface Shoutoutrepository extends JpaRepository<Shoutout, String> {

}
