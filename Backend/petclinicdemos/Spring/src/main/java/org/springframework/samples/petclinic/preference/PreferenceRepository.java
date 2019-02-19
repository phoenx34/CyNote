package org.springframework.samples.petclinic.preference;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */

public interface PreferenceRepository extends JpaRepository<Preference, String> {

}
