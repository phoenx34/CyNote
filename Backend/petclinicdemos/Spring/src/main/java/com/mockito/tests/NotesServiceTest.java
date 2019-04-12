package com.mockito.tests;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.samples.petclinic.notes.Notes;
import org.springframework.samples.petclinic.notes.NotesRepository;
import org.springframework.samples.petclinic.notes.NotesService;


public class NotesServiceTest {

	@InjectMocks
	NotesService notesService;

	@Mock
	NotesRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
	}

	

	

	
	/**
	 * Test 1
	 */
	@Test
	public void getNotesByIdTest() {
		Notes notes = new Notes();
		notes.setNID(1);
		notes.setTitle("Hello");
		notes.setRating(50);
		when(repo.getNotesByID(1)).thenReturn(notes);

		Notes notesNew = notesService.findNotesById(1);

		assertEquals(1, notesNew.getNID().intValue());
		assertEquals("Hello", notes.getTitle());
		assertEquals(50, notes.getRating());
	
	}
	
	
	
	
	
	



	@Test
	public void getAllNotesTest() {
		
//    	this.UID = 123456789;
//    	this.screenname = "defaultconstructor";
//    	this.password = "passssssword";
//    	this.create_time = "12:12:12";
//    	this.email = "hotmail";
//    	this.type = "notes";
		
		
		List<Notes> list = new ArrayList<Notes>();
		Notes notes = new Notes();
		notes.setNID(1);
		notes.setTitle("hello_1");
		notes.setRating(50);
		
		
		
		Notes notes2 = new Notes();
		notes2.setNID(2);
		notes2.setTitle("hello_2");
		notes2.setRating(40);
	
		
		Notes notes3 = new Notes();
		notes3.setNID(3);
		notes3.setTitle("hello_3");
		notes3.setRating(30);

		
		
		
		list.add(notes);
		list.add(notes2);
		list.add(notes3);

		when(repo.findAll()).thenReturn(list);

		List<Notes> notesList = notesService.findAllNotes();

		assertEquals(3, notesList.size());
		verify(repo, times(1)).findAll();
	}


	
}
