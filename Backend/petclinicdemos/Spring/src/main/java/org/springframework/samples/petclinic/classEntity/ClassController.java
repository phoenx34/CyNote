package org.springframework.samples.petclinic.classEntity;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.textbook.Textbook;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */

@RestController
public class ClassController {
	
    @Autowired
    ClassRepository classRepository;

    private final Logger logger = LoggerFactory.getLogger(ClassController.class);

    @PostMapping("/classes")
    public ResponseEntity<Object> saveClass(classEntity oneClass) {
    	classEntity savedClass = classRepository.save(oneClass);  	
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") 			
    			.buildAndExpand(savedClass.getCID()).toUri();  	
    	return ResponseEntity.created(location).build();  

    }

    @RequestMapping(method = RequestMethod.GET, path = "/class")
    public List<classEntity> getAllClasses() {
        logger.info("Entered into Controller Layer");
        List<classEntity> results = (List<classEntity>) classRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/classes/{id}")
    public List<String> getTextbooks(@PathVariable("id") Integer id) {
    	logger.info("Entered into Controller Layer");
    	Optional<classEntity> thisClass = this.findClassById(id);
    	if(thisClass.isPresent() == false) {
    		return null;
    	}
    	
    	List<String> textbooks = null;
    	classEntity temp = thisClass.get();
    	List<Textbook> texts = temp.getTextbooks();
    	texts.toArray();
    	
    	for(int i = 0; i < texts.size(); i++) {
    		Textbook text = texts.get(i);
    		textbooks.add(text.getBookLink());
    	}
    	
    	return textbooks;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/classes/{id}")
    public Optional<classEntity> findClassById(@PathVariable("id") Integer id) {
        logger.info("Entered into Controller Layer");
        Optional<classEntity> results = classRepository.findById(id);
        return results;
    }
}
