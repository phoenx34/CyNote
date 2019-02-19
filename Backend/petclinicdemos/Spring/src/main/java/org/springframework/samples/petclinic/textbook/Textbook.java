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
    @Column(name = "Class number")    
    @NotFound(action = NotFoundAction.IGNORE)
    private String classNum;        // Class number
    
    @Column(name = "TID")    
    @NotFound(action = NotFoundAction.IGNORE)
    private String TID;        // Textbook ID
    
    @Column(name = "Book link")    
    @NotFound(action = NotFoundAction.IGNORE)
    private String bookLink;        // Book link
    
    @Column(name = "Rating")    
    @NotFound(action = NotFoundAction.IGNORE)
    private int rating;        // Rating of the textbook
                               // ???????? should the rating be integer

    
    // ?????????? Is the lecture number necessary here
//    @Column(name = "Lecture number")    
//    @NotFound(action = NotFoundAction.IGNORE)
//    private String lecNum;        // Lecture number
    
    
    // Getters and Setters 
    public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public String getTID() {
		return TID;
	}

	public void setTID(String tID) {
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

	
	// toString method 
	@Override
	public String toString() {
		return "Textbook [classNum=" + classNum + ", TID=" + TID + ", bookLink=" + bookLink + ", rating=" + rating
				+ "]";
	}
    
	
	
    

    
    
    
}
