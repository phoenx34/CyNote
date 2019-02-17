package org.springframework.samples.petclinic.owner;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */

@RestController
public class PreferenceController {
	
	@Autowired
	PreferenceRepository preferenceRepository;

    private final Logger logger = LoggerFactory.getLogger(PreferenceController.class);

    
    
    // ????????This is problematic since we don't have a preferenceID
    @RequestMapping(method = RequestMethod.POST, path = "/preference/new")
    public String saveNote(Preference preference) {
    	preferenceRepository.save(preference);
        return "New Preference "+ preference.getFavorites() + " Saved";
    }

    
    
    
    @RequestMapping(method = RequestMethod.GET, path = "/preference")
    public List<Preference> getAllPreferences() {
        logger.info("Entered into Controller Layer");
        List<Preference> results = preferenceRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    
    
    
    
    // ????????This is problematic since we don't have a preferenceID
    @RequestMapping(method = RequestMethod.GET, path = "/preference/{preferenceId}")  // ????????? Have to change this line
    public Optional<Preference> findPreferenceById(@PathVariable("preferenceId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Preference> results = preferenceRepository.findById(id);
        return results;
    }
}
