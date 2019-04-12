package org.springframework.samples.petclinic.preference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.*;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.samples.petclinic.classEntity.ClEnt;
import org.springframework.samples.petclinic.user.User;

@Entity
@Table(name = "preference")
public class Preference {
	
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PID")    
    private int PID;        // User ID
	
    @Column(name = "Start_page")    
    private String startPage;        // Start Page
                                     // ??????? Store the page number of the start page
    
    @Column(name = "Favorites")    
    private String favorites;        // Favorite pages
                                     // ??????? Maybe use an arraylist instead or string
    
    
    @Column(name = "Color_preferences")    
    private String colorPreferences;    // Color preferences

    
    
    
    // This is a many to one relationship with the class
    /*@OneToOne(mappedBy = "preference")
    private User users;*/

    
    
    // Getters and Setters

	public int getPID() {
		return PID;
	}




	public void setPID(int pID) {
		PID = pID;
	}




	public String getStartPage() {
		return startPage;
	}




	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}




	public String getFavorites() {
		return favorites;
	}




	public void setFavorites(String favorites) {
		this.favorites = favorites;
	}




	public String getColorPreferences() {
		return colorPreferences;
	}




	public void setColorPreferences(String colorPreferences) {
		this.colorPreferences = colorPreferences;
	}

    
	
	


	
	// toString method 
	// ???????? need the UID to be in the String
	@Override
	public String toString() {
		return "Preference [startPage=" + startPage + ", favorites=" + favorites + ", colorPreferences="
				+ colorPreferences + "]";
	}




	
	
}
