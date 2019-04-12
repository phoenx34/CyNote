package org.springframework.samples.petclinic.textbook;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public class TextbookService {

	 @Autowired          
	 TextbookRepository textbookRepository;
	 
	 
	 public Textbook findTextbookById(Integer TID) 
	 {
		 return textbookRepository.getTextbookByID(TID);
	 }
	 
	 
	 public List<Textbook> findAllTextbooks()
	 {
		 return textbookRepository.findAll();
	 }
	
}
