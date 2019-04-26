package org.springframework.samples.petclinic.textbook;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TextbookRepository that's connect to the database 
 * @author Shen Chen
 * @author Marc Issac
 */
public interface TextbookRepository extends JpaRepository<Textbook, String>{

}
