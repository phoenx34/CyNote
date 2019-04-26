package org.springframework.samples.petclinic.notes;

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
import org.springframework.samples.petclinic.classEntity.ClEnt;
import org.springframework.samples.petclinic.lectureEntity.Lecture;

/**
 * Notes represents the note object with all the information of a note 
 * @author Shen Chen
 * @author Marc Issac
 **/
@Entity
@Table(name = "notes")
public class Notes {
	
	// This is a many to one relationship with the class
    @ManyToOne
    @JoinColumn(name="lid")
    private Lecture lecture;
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NID")
    private Integer NID;      // Note ID            
	

	@Column(name = "Lecture_number")
    private Integer lecNum;     // Lecture number
	
	@Column(name = "Title")
    private String title;      // The title of the note
	
	@Column(name = "Address")
    private String address;      // The address of the note,  in the form of URl, the URl where it's uploaded to the server
	
	@Column(name = "Rating")
    private int rating;      // Rating of the note to determine the quality of the note  
	
	
	
	
	
	
	// getters and setters
	
	
	
	
	
	
	
	/**
	 * Getter for NID 
	 * @return NID
	 */
	public Integer getNID() {
		return NID;
	}

	
	/**
	 * Setter for NID 
	 * @param nID Given NID
	 */
	public void setNID(Integer nID) {
		NID = nID;
	}


	/**
	 * Getter for getLecNum
	 * @return getLecNum
	 */
	public Integer getLecNum() {
		return lecNum;
	}

	/**
	 * Setter for getLecNum
	 * @param lecNum Given lecture number
	 */
	public void setLecNum(Integer lecNum) {
		this.lecNum = lecNum;
	}

	/**
	 * Getter for title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title
	 * @param title Given title 
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for address
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Setter for address
	 * @param address The given address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Getter for Rating 
	 * @return rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Setter for rating
	 * @param rating the setter for rating
	 */ 
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * Increase the rating of the note by one 
	 */
	public void increaseRateingByOne() {
		rating += 1;
	}
	
	
	
	
}
