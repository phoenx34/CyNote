package org.springframework.samples.petclinic.classEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.user.User;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */

@Repository
public interface ClassRepository extends CrudRepository<classEntity, Integer> {
	classEntity save(classEntity persisted); 
	Optional<classEntity> findById(Integer CID);
	List<classEntity> findAll();
	

}
