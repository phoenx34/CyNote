package com.mockito.tests;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.samples.petclinic.textbook.Textbook;
import org.springframework.samples.petclinic.textbook.TextbookRepository;
import org.springframework.samples.petclinic.textbook.TextbookService;


public class TextbookServiceTest {

	@InjectMocks
	TextbookService textbookService;

	@Mock
	TextbookRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
	}

	

	

	
	/**
	 * Test 1
	 */
	@Test
	public void getTextbookByIdTest() {
		Textbook textbook = new Textbook();
		textbook.setTID(1);
		textbook.setBookLink("www.amazon.com");
		textbook.setRating(50);
		when(repo.getTextbookByID(1)).thenReturn(textbook);

		Textbook textbookNew = textbookService.findTextbookById(1);

		assertEquals(1, textbookNew.getTID().intValue());
		assertEquals("www.amazon.com", textbook.getBookLink());
		assertEquals(50, textbook.getRating());
	
	}
	
	
	
	
	
	



	@Test
	public void getAllTextbookTest() {
		
//    	this.UID = 123456789;
//    	this.screenname = "defaultconstructor";
//    	this.password = "passssssword";
//    	this.create_time = "12:12:12";
//    	this.email = "hotmail";
//    	this.type = "textbook";
		
		
		List<Textbook> list = new ArrayList<Textbook>();
		Textbook textbook = new Textbook();
		textbook.setTID(1);
		textbook.setBookLink("www.amazon.com");
		textbook.setRating(50);
		
		
		
		Textbook textbook2 = new Textbook();
		textbook2.setTID(2);
		textbook2.setBookLink("www.amazon.com");
		textbook2.setRating(40);
	
		
		Textbook textbook3 = new Textbook();
		textbook3.setTID(3);
		textbook3.setBookLink("www.amazon.com");
		textbook3.setRating(30);

		
		
		
		list.add(textbook);
		list.add(textbook2);
		list.add(textbook3);

		when(repo.findAll()).thenReturn(list);

		List<Textbook> textbookList = textbookService.findAllTextbooks();

		assertEquals(3, textbookList.size());
		verify(repo, times(1)).findAll();
	}


	
}
