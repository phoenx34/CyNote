package org.springframework.samples.petclinic.textbook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */



@Entity
@Table(name = "textbook")
public class Textbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TID")    
    private String TID;        // Textbook ID   
    
    @Column(name = "Class_CID")    
    private int class_CID;        // The class ID, how we identify which class
    
    @Column(name = "Book_link")    
    private String bookLink;        // Book link
    
    @Column(name = "Rating")    
    private int rating;        // Rating of the textbook
                               // ???????? should the rating be integer

    

    
    
    
    // Getters and Setters 
	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
		TID = tID;
	}

	public int getClass_CID() {
		return class_CID;
	}

	public void setClass_CID(int class_CID) {
		this.class_CID = class_CID;
	}

	public String getBookLink() {
		return bookLink;
	}

	public void setBookLink(String bookLink) {
		this.bookLink = bookLink;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

    

   
	
	
    

    
    
    
}
