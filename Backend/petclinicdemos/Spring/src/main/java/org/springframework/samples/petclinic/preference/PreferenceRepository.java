package org.springframework.samples.petclinic.preference;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PreferenceRepository connects with the database 
 * @author Shen Chen
 * @author Marc Issac
 */
public interface PreferenceRepository extends JpaRepository<Preference, String> {

}
