package org.springframework.samples.petclinic.notes;

import java.util.ArrayList;
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
 **/

@RestController
public class NotesController {

	@Autowired
	NotesRepository noteRepository;

	@Autowired
	NotesService notesService;
	
    private final Logger logger = LoggerFactory.getLogger(NotesController.class);

    @RequestMapping(method = RequestMethod.POST, path = "/notes/new")
    public String saveNote(Notes note) {
    	noteRepository.save(note);
        return "New Note "+ note.getNID() + " Saved";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/notes")
    public Notes[] getAllNotes() {
        logger.info("Entered into Controller Layer");
        List<Notes> results = noteRepository.findAll();
        
        Notes[] arr = new Notes[results.size()];
        
        for(int i = 0; i < arr.length; i++) {
        	arr[i] = results.get(i);
        }
        
        
       return arr;
    }
    
    /**
     * Finds specific subset of notes and sorts them based upon
     * each's popularity from high to low.
     * @param lid lecture_id
     * @return Array of notes sorted by popularity
     */
    @RequestMapping(method = RequestMethod.GET, path = "/getNotes/{lid}")
    public Notes[] getNotesLID(@PathVariable("lid") int lid) {
		List<Notes> x = new ArrayList<Notes>();
		for(Notes n : getAllNotes()) {
			if(n.getLecNum() == lid) {
				x.add(n);
			}
		}
		
		Notes[] toSort = new Notes[x.size()];
        
        for(int i = 0; i < toSort.length; i++) {
        	toSort[i] = x.get(i);
        }
		
    	//Notes[] toSort = (Notes[]) x.toArray();
    	toSort = notesService.quicksortNote(toSort);
    	return toSort;
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/updateNote/{nid}/{lid}")
    public Notes[] updateNotes(@PathVariable("nid") int nid, @PathVariable("lid") int lid) {
    	
    	List<Notes> notes = noteRepository.findAll();
    	Notes upMe = noteRepository.findById(nid).get();
    	if(upMe == null) {
    		return null;
    	}
    	
    	noteRepository.deleteById(nid);
    	upMe.setRating(upMe.getRating() + 1);
    	noteRepository.save(upMe);
    	
    	return getNotesLID(lid);
    }
    
    
    @RequestMapping(method = RequestMethod.GET, path = "/notes/{noteId}")
    public Optional<Notes> findNoteById(@PathVariable("noteId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Notes> results = noteRepository.findById(id);
        return results;
    }

	
}
