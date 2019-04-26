package org.springframework.samples.petclinic.notes;

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
 * The NotesController direct all the json request to specific methods 
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

    /**
     * Posting a new note 
     * @param note New note to be added to the database 
     * @return if it's saved
     */
    @RequestMapping(method = RequestMethod.POST, path = "/notes/new")
    public String saveNote(Notes note) {
    	noteRepository.save(note);
        return "New Note "+ note.getNID() + " Saved";
    }

    /**
     * Getting all the notes 
     * @return Array of notes 
     */
    @RequestMapping(method = RequestMethod.GET, path = "/notes")
    public Notes[] getAllNotes() {
        logger.info("Entered into Controller Layer");
        List<Notes> results = noteRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        Notes[] result = notesService.quicksortNote((Notes[]) results.toArray());
       return result;
    }
    
    
    
    /**
     * Find the note by note ID 
     * @param id The given Id 
     * @return The desired Note object 
     */
    @RequestMapping(method = RequestMethod.GET, path = "/notes/{noteId}")
    public Optional<Notes> findNoteById(@PathVariable("noteId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Notes> results = noteRepository.findById(id);
        return results;
    }

	
}
