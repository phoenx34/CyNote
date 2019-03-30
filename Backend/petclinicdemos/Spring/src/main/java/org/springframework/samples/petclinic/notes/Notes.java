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
 **/

@Entity
@Table(name = "notes")
public class Notes {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NID")
    private String NID;      // Note ID            
	
	@Column(name = "Class_number")
    private String classNum;      // Class number

	@Column(name = "Lecture_number")
    private String lecNum;      // Lecture number
	
	@Column(name = "Title")
    private String title;      // The title of the note
	
	@Column(name = "Address")
    private String address;      // The address of the note,  in the form of URl, the URl where it's uploaded to the server
	
	@Column(name = "Rating")
    private int rating;      // Rating of the note to determine the quality of the note  
	
	@Column(name = "Content")
    private String content;      // Content of the note

	
	
	
	
	// getters and setters
	public String getNID() {
		return NID;
	}

	public void setNID(String nID) {
		NID = nID;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
	
	
	

	
	
	
	
	
	
	
	
	
}
