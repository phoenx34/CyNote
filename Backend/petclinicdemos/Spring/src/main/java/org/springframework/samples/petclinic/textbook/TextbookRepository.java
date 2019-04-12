package org.springframework.samples.petclinic.textbook;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */

public interface TextbookRepository extends JpaRepository<Textbook, Integer>{
	Textbook save(Textbook persisted);
	Optional<Textbook> findById(Integer TID);
	List<Textbook> findAll();
	Textbook getTextbookByID(Integer id);
}
