package org.springframework.samples.petclinic.lectureEntity;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.classEntity.ClassRepository;
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
@RequestMapping("/api")
public class LectureController {
  @Autowired
  private LectureRepository lectureRepository;
  
  @Autowired
  private ClassRepository classRepository;
  
    @GetMapping("/classes/{classId}/lecture")
    public List<Lecture> getContactByClassId(@PathVariable Long classId) {
      
        if(!classRepository.existsById(classId)) {
            throw new NotFoundException("Class not found!");
        }
      
      return lectureRepository.findByClassId(classId);
    }
    
    
    
    
    @PostMapping("/classes/{classId}/lecture")
    public Lecture addLecture(@PathVariable Long classId,
                            @Valid @RequestBody Lecture lecture) {
        return classRepository.findById(classId)
                .map(classEnt -> {
                	lecture.setClEnt(classEnt);
                    return lectureRepository.save(lecture);
                }).orElseThrow(() -> new NotFoundException("Class not found!"));
    }
    
    
    
    
    
    @PutMapping("/classes/{classId}/lecture/{lectureId}")
    public Lecture updateLecture(@PathVariable Long classId,
                    @PathVariable Long lectureId,
                    @Valid @RequestBody Lecture lectureUpdated) {
      
      if(!classRepository.existsById(classId)) {
        throw new NotFoundException("Class not found!");
      }
      
        return lectureRepository.findById(lectureId)
                .map(lecture -> {
//                    lecture.setName(lectureUpdated.getName());
//                    lecture.setGrade(lectureUpdated.getGrade());
                    return lectureRepository.save(lecture);
                }).orElseThrow(() -> new NotFoundException("Lecture not found!"));
    }
    

    
    
    
    
    
    
    
    
    
    
    
    
    
    @DeleteMapping("/classes/{classId}/lecture/{lectureId}")
    public String deleteLecture(@PathVariable Long classId,
                     @PathVariable Long lectureId) {
      
      if(!classRepository.existsById(classId)) {
        throw new NotFoundException("class not found!");
      }
      
        return lectureRepository.findById(lectureId)
                .map(lecture -> {
                    lectureRepository.delete(lecture);
                    return "Deleted Successfully!";
                }).orElseThrow(() -> new NotFoundException("Contact not found!"));










    }}



















//package org.springframework.samples.petclinic.lectureEntity;
//import java.net.URI;
//import java.util.List;
//import java.util.Optional;
//
//import javax.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.samples.petclinic.classEntity.ClassRepository;
//import org.springframework.samples.petclinic.classEntity.ClEnt;
//import org.springframework.samples.petclinic.notes.Notes;
//import org.springframework.samples.petclinic.notes.NotesRepository;
//import org.springframework.samples.petclinic.shoutout.Shoutout;
//import org.springframework.samples.petclinic.user.User;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import javassist.NotFoundException;
//
//
//// have to have a lecture first in the 
//public class LectureController {
//
//	@Autowired
//	LectureRepository lectureRepository;
//	@Autowired
//	NotesRepository notesRepo;
//	@Autowired
//	ClassRepository classRepo;
//
//	private final Logger logger = LoggerFactory.getLogger(LectureController.class);
//
//	
//	 /*@RequestMapping(method = RequestMethod.GET, path = "/noteslist/{id}")
//	    public String getLecture(@PathVariable("id") Integer id) {
//	    	logger.info("Entered into Controller Layer");
//	    	logger.info("id: " + id);
//	    	Optional<Lecture> results = lectureRepository.findById(id);
//	    	logger.info("classid: " + results.get().getLid());
//	    	if(results.isPresent() == false)
//	    		return null;
//	    	Lecture curr = results.get();
//	    	List<Notes> notes = curr.getNotes();
//	    	logger.info("size of class list: " + notes.size());
//	    	//classes.toArray();
//
//	    	String result = "{\"lectures\":[";
//
//	    	for(int i=0; i < notes.size(); i++) {
//
//	    	   result += "{\"nid\":\"" + notes.get(i).getNID()+"\",";
//	    	   result += "{\"title\":\"" + notes.get(i).getTitle()+"\",";
//	    	   result += "{\"address\":\"" + notes.get(i).getAddress()+"\",";
//	    	   result += "{\"rating\":\"" + notes.get(i).getRating()+"\",";
//
//	    	}
//	    	
//	    	//Removes final comma
//	    	result = result.replaceAll(", $", "");
//	    	result += "]}";
//	    	logger.info("Result is: " + result);
//	    	return result;
//	 }*/
//	
//	@RequestMapping(method = RequestMethod.GET, path = "/classent/{clEntId}/lectures")
//	public List<Lecture> getLecturesByCID(@PathVariable Integer clEntId) throws NotFoundException {
//		if(!classRepo.existsById(clEntId)) {
//			throw new NotFoundException("Class not found!");
//		}
//		
//		return lectureRepository.findByClEntId(clEntId);
//	}
//	 
//	@PostMapping("/classent/{clEntId}/lectures")
//    public Lecture saveClass(@PathVariable Integer clEntId, @Valid @RequestBody Lecture lecture) throws NotFoundException {
//		
//		
//		return classRepo.findById(clEntId)
//				.map(classent -> {
//					lecture.setClassEnt(classent);
//					return lectureRepository.save(lecture);
//				}).orElseThrow(() -> new NotFoundException("Class not found!"));
//		
//		/*Lecture lecture = new Lecture(lid, cur);
//    	logger.info("1111111111");
//    	Lecture savedClass = lectureRepository.save(lecture);
//    	logger.info("22222222");
//    	classEntity curclass = lecture.getClassEnt();
//    	logger.info("classid is: " + curclass.getCID());
//		curclass.addLecture(lecture);
//		classRepo.deleteById(curclass.getCID());
//		classRepo.save(curclass);
//    	
//    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") 			
//    			.buildAndExpand(savedClass.getLid()).toUri();  	
//    	return ResponseEntity.created(location).build();  */
//
//    }
//	
//	
//	
//}
