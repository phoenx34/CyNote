package org.springframework.samples.petclinic.notes;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 **/


public interface NotesRepository extends JpaRepository<Notes, Integer> {
	Notes save(Notes persisted);
	List<Notes> findAll();
	Optional<Notes> findById(Integer uid);
}
