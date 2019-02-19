package org.springframework.samples.petclinic.notes;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */


public interface NotesRepository extends JpaRepository<Notes, String> {

}
