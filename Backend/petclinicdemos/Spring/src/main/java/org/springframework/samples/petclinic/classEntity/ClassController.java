package org.springframework.samples.petclinic.classEntity;

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
public class ClassController {
	
    @Autowired
    ClassRepository classRepository;

    private final Logger logger = LoggerFactory.getLogger(ClassController.class);

    @RequestMapping(method = RequestMethod.POST, path = "/class/new")
    public String saveClass(classEntity oneClass) {
    	classRepository.save(oneClass);
        return "New class "+ oneClass.getCID() + " Saved";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/class")
    public List<classEntity> getAllClasses() {
        logger.info("Entered into Controller Layer");
        List<classEntity> results = classRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/class/{classId}")
    public Optional<classEntity> findClassById(@PathVariable("classID") String id) {
        logger.info("Entered into Controller Layer");
        Optional<classEntity> results = classRepository.findById(id);
        return results;
    }
}
