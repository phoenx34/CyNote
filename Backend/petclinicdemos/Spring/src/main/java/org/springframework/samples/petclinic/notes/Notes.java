package org.springframework.samples.petclinic.notes;

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
@Table(name = "notes")
public class Notes {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NID")
    private String NID;      // Note ID
	
	@Column(name = "Rating")
    private int rating;      // Rating of the note to determine the quality of the note
	                            // ???? Possibly change it to String

	@Column(name = "Content")
    private String content;      // Content of the note
	
	@Column(name = "Class_number")
    private String classNum;      // Class number
	
	@Column(name = "Lecture_number")
    private String lecNum;      // Lecture number

	
	
	
	// Getters and Setters
	
	public String getNID() {
		return NID;
	}

	public void setNID(String nID) {
		NID = nID;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public String getLecNum() {
		return lecNum;
	}

	public void setLecNum(String lecNum) {
		this.lecNum = lecNum;
	}

	
	
	
	// toString Method
	@Override
	public String toString() {
		return "Notes [NID=" + NID + ", classNum=" + classNum
				+ ", lecNum=" + lecNum + ", rating=" + rating + ", content=" + content +  "]";
	}
	

	
	
	
	
	
	
	
	
	
}
