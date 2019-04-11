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
    private Integer NID;      // Note ID            
	
	@Column(name = "Class_number")
    private Integer classNum;      // Class number

	@Column(name = "Lecture_number")
    private Integer lecNum;      // Lecture number
	
	@Column(name = "Title")
    private String title;      // The title of the note
	
	@Column(name = "Address")
    private String address;      // The address of the note,  in the form of URl, the URl where it's uploaded to the server
	
	@Column(name = "Rating")
    private int rating;      // Rating of the note to determine the quality of the note  
	
	
	
	
	
	
	// getters and setters
	public Integer getNID() {
		return NID;
	}

	public void setNID(Integer nID) {
		NID = nID;
	}

	public Integer getClassNum() {
		return classNum;
	}

	public void setClassNum(Integer classNum) {
		this.classNum = classNum;
	}

	public Integer getLecNum() {
		return lecNum;
	}

	public void setLecNum(Integer lecNum) {
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

	
}