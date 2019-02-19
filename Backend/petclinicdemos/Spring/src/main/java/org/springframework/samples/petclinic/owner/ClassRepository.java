package org.springframework.samples.petclinic.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */

@Repository
public interface ClassRepository extends JpaRepository<Class, String> {

}
