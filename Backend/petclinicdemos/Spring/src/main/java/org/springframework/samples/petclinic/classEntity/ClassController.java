package org.springframework.samples.petclinic.classEntity;
 
import java.util.List;
import java.util.Optional;
 
import javax.validation.Valid;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.exception.NotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 


@RestController
public class ClassController {
  
  @Autowired
  private ClassRepository classRepository;
  
    @GetMapping("/classes")
    public List<ClEnt> getAllClasses() {
      return classRepository.findAll();
    }
    
    @GetMapping("/classes/{id}")
    public ClEnt getClassByID(@PathVariable Long id) {
      Optional<ClEnt> optClass = classRepository.findById(id);
      if(optClass.isPresent()) {
        return optClass.get();
      }else {
        throw new NotFoundException("Class not found with id " + id);
      }
    }
    
    
    @PostMapping("/classes")
    public ClEnt createClass(@Valid @RequestBody ClEnt classEnt) {
        return classRepository.save(classEnt);
    }
    
    
    
    
    @PutMapping("/classes/{id}")
    public ClEnt updateStudent(@PathVariable Long id,
                                   @Valid @RequestBody ClEnt classUpdated) {
        return classRepository.findById(id)
                .map(classEnt -> {
//                	classEnt.setAge(classUpdated.getAge());
                    return classRepository.save(classEnt);
                }).orElseThrow(() -> new NotFoundException("Class not found with id " + id));
    }
    
    
    
    
    @DeleteMapping("/classes/{id}")
    public String deleteClass(@PathVariable Long id) {
        return classRepository.findById(id)
                .map(classEnt -> {
                    classRepository.delete(classEnt);
                    return "Delete Successfully!";
                }).orElseThrow(() -> new NotFoundException("Class not found with id " + id));
    }
}




























//package org.springframework.samples.petclinic.classEntity;
//
//import java.net.URI;
//import java.util.List;
//import java.util.Optional;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.samples.petclinic.lectureEntity.Lecture;
//import org.springframework.samples.petclinic.shoutout.Shoutout;
//import org.springframework.samples.petclinic.textbook.Textbook;
//import org.springframework.samples.petclinic.user.User;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
///**
// * 
// * @author Shen Chen
// * @author Marc Issac
// */
//
//@RestController
//public class ClassController {
//	
//    @Autowired
//    ClassRepository classRepository;
//
//    private final Logger logger = LoggerFactory.getLogger(ClassController.class);
//
//    @PostMapping("/classent")
//    public ResponseEntity<Object> saveClass(@RequestBody ClEnt oneClass) {
//    	ClEnt savedClass = classRepository.save(oneClass);  	
//    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") 			
//    			.buildAndExpand(savedClass.getCID()).toUri();  	
//    	return ResponseEntity.created(location).build();  
//
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/classent")
//    public List<ClEnt> getAllClasses() {
//        logger.info("Entered into Controller Layer");
//        List<ClEnt> results = (List<ClEnt>) classRepository.findAll();
//        logger.info("Number of Records Fetched:" + results.size());
//        return results;
//    }
//    
//    @RequestMapping(method = RequestMethod.GET, path = "/textbooklist/{id}")
//    public List<String> getTextbook(@PathVariable("id") Integer id) {
//    	logger.info("Entered into Controller Layer");
//    	Optional<ClEnt> thisClass = this.findClassById(id);
//    	if(thisClass.isPresent() == false) {
//    		return null;
//    	}
//    	
//    	List<String> textbooks = null;
//    	ClEnt temp = thisClass.get();
//    	List<Textbook> texts = temp.getTextbooks();
//    	texts.toArray();
//    	
//    	for(int i = 0; i < texts.size(); i++) {
//    		Textbook text = texts.get(i);
//    		textbooks.add(text.getBookLink());
//    	}
//    	
//    	return textbooks;
//    }
//    
//    // Deprecated
//   /* @RequestMapping(method = RequestMethod.GET, path = "/lecturelist/{id}")
//    public String getLecture(@PathVariable("id") Integer id) {
//    	logger.info("Entered into Controller Layer");
//    	logger.info("id: " + id);
//    	Optional<classEntity> results = classRepository.findById(id);
//    	logger.info("classid: " + results.get().getCID());
//    	if(results.isPresent() == false)
//    		return null;
//    	classEntity curr = results.get();
//    	List<Lecture> lectures = curr.getLecture();
//    	logger.info("size of class list: " + lectures.size());
//    	//classes.toArray();
//
//    	String result = "{\"lectures\":[";
//
//    	for(int i=0; i < lectures.size(); i++) {
//
//    	   result += "{\"cid\":\"" + lectures.get(i).getLid()+"\",";
//
//    	}
//
//    	//Removes final comma
//    	result = result.replaceAll(", $", "");
//    	result += "]}";
//    	logger.info("Result is: " + result);
//    	return result;
//    }*/
//    
//    @RequestMapping(method = RequestMethod.GET, path = "/shoutoutlist/{id}")
//    public List<String> getShoutout(@PathVariable("id") Integer id) {
//    	logger.info("Entered into Controller layer, poop");
//    	Optional<ClEnt> thisClass = this.findClassById(id);
//    	if(thisClass.isPresent() == false) {
//    		return null;
//    	}
//    	
//    	List<String> shoutouts = null;
//    	ClEnt temp = thisClass.get();
//    	List<Shoutout> shouts = temp.getShoutouts();
//    	shouts.toArray();
//    	
//    	for(int i = 0; i < shouts.size(); i++) {
//    		Shoutout shout = shouts.get(i);
//    		shoutouts.add(shout.getContent());
//    	}
//    	
//    	return shoutouts;
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/classent/{id}")
//    public Optional<ClEnt> findClassById(@PathVariable("id") Integer id) {
//        logger.info("Entered into Controller Layer");
//        Optional<ClEnt> results = classRepository.findById(id);
//        return results;
//    }
//}
