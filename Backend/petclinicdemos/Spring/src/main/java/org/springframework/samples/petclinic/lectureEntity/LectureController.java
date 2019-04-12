package org.springframework.samples.petclinic.lectureEntity;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.classEntity.ClassRepository;
import org.springframework.samples.petclinic.classEntity.classEntity;
import org.springframework.samples.petclinic.notes.Notes;
import org.springframework.samples.petclinic.notes.NotesRepository;
import org.springframework.samples.petclinic.shoutout.Shoutout;
import org.springframework.samples.petclinic.user.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


// have to have a lecture first in the 
public class LectureController {

	@Autowired
	LectureRepository lectureRepository;
	@Autowired
	NotesRepository notesRepo;
	@Autowired
	ClassRepository classRepo;

	private final Logger logger = LoggerFactory.getLogger(LectureController.class);

	
	 /*@RequestMapping(method = RequestMethod.GET, path = "/noteslist/{id}")
	    public String getLecture(@PathVariable("id") Integer id) {
	    	logger.info("Entered into Controller Layer");
	    	logger.info("id: " + id);
	    	Optional<Lecture> results = lectureRepository.findById(id);
	    	logger.info("classid: " + results.get().getLid());
	    	if(results.isPresent() == false)
	    		return null;
	    	Lecture curr = results.get();
	    	List<Notes> notes = curr.getNotes();
	    	logger.info("size of class list: " + notes.size());
	    	//classes.toArray();

	    	String result = "{\"lectures\":[";

	    	for(int i=0; i < notes.size(); i++) {

	    	   result += "{\"nid\":\"" + notes.get(i).getNID()+"\",";
	    	   result += "{\"title\":\"" + notes.get(i).getTitle()+"\",";
	    	   result += "{\"address\":\"" + notes.get(i).getAddress()+"\",";
	    	   result += "{\"rating\":\"" + notes.get(i).getRating()+"\",";

	    	}
	    	
	    	//Removes final comma
	    	result = result.replaceAll(", $", "");
	    	result += "]}";
	    	logger.info("Result is: " + result);
	    	return result;
	 }*/
	
	@RequestMapping(method = RequestMethod.GET, path = "/lectures")
    public List<Lecture> getAllLectures() {
        logger.info("Entered into Controller Layer");
        List<Lecture> results = lectureRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
	 
	@PostMapping("/lectures")
    public ResponseEntity<Object> saveClass(@RequestBody Lecture lecture) {
		
		
    	logger.info("1111111111");
    	Lecture savedClass = lectureRepository.save(lecture);
    	logger.info("22222222");
    	classEntity curclass = lecture.getClassEnt();
    	logger.info("classid is: " + curclass.getCID());
		curclass.addLecture(lecture);
		classRepo.deleteById(curclass.getCID());
		classRepo.save(curclass);
    	
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") 			
    			.buildAndExpand(savedClass.getLid()).toUri();  	
    	return ResponseEntity.created(location).build();  

    }
	
	
	
}
