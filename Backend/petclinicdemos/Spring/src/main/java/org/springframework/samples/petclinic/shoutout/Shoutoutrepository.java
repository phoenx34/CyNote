package org.springframework.samples.petclinic.shoutout;

import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */

public interface Shoutoutrepository extends JpaRepository<Shoutout, String> {

}
