package org.springframework.samples.petclinic.shoutout;

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
 * ShoutoutController direct all the json request to specific methods
 * @author Shen Chen
 * @author Marc Issac
 */ 
@RestController
public class ShoutoutController {

	@Autowired
	Shoutoutrepository shoutoutrepository;

    private final Logger logger = LoggerFactory.getLogger(ShoutoutController.class);

    /**
     * Saving a new shoutout in the database  
     * @param shoutout The shoutout to be added to the database 
     * @return Whether the shoutout was saved 
     */
    @RequestMapping(method = RequestMethod.POST, path = "/shoutout/new")
    public String saveShoutout(Shoutout shoutout) {
    	shoutoutrepository.save(shoutout);
        return "New Shoutout "+ shoutout.getSID() + " Saved";
    }

    /**
     * List all the shoutout
     * @return The list of shoutouts 
     */
    @RequestMapping(method = RequestMethod.GET, path = "/shoutout")
    public List<Shoutout> getAllShoutouts() {
        logger.info("Entered into Controller Layer");
        List<Shoutout> results = shoutoutrepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    
    
    
    
    /*@RequestMapping(method = RequestMethod.GET, path = "/shoutout/{shoutoutId}")
    public Optional<Shoutout> findShoutoutById(@PathVariable("shoutoutId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Shoutout> results = shoutoutrepository.findById(id);
        return results;
    }*/

}
