package org.springframework.samples.petclinic.textbook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.samples.petclinic.classEntity.classEntity;

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
    private Integer TID;        // Textbook ID   
    
 
    @Column(name = "Book_link")    
    private String bookLink;        // Book link
    
    @Column(name = "Rating")    
    private int rating;        // Rating of the textbook
     
    
    
    // This is a many to one relationship with the class
    @ManyToOne
    @JoinColumn(name="Class_CID")
    private classEntity classentity;
    

    
    
    
    // Getters and Setters 
	public Integer getTID() {
		return TID;
	}

	public void setTID(Integer tID) {
		TID = tID;
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
