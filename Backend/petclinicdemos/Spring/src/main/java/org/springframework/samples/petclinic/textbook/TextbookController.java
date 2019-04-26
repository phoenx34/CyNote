package org.springframework.samples.petclinic.textbook;

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
 * TextbookController direct all the json request to specific methods
 * @author Shen Chen
 * @author Marc Issac
 */
@RestController
public class TextbookController {

	@Autowired
	TextbookRepository textbookRepository;

    private final Logger logger = LoggerFactory.getLogger(TextbookController.class);

    /**
     * Save a new textbook in the database 
     * @param textbook the new to be saved 
     * @return Whether the textbook has been saved 
     */
    @RequestMapping(method = RequestMethod.POST, path = "/textbook/new")
    public String saveTextbook(Textbook textbook) {
    	textbookRepository.save(textbook);
        return "New Textbook "+ textbook.getTID() + " Saved";
    }

    /**
     * Return a list of textbook
     * @return a list of textbook
     */
    @RequestMapping(method = RequestMethod.GET, path = "/textbook")
    public List<Textbook> getAllTextbooks() {
        logger.info("Entered into Controller Layer");
        List<Textbook> results = textbookRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    /*@RequestMapping(method = RequestMethod.GET, path = "/textbook/{textbookId}")
    public Optional<Textbook> findTextbookById(@PathVariable("textbookId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Textbook> results = textbookRepository.findById(id);
        return results;
    }*/
}
