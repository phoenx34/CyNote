package org.springframework.samples.petclinic.preference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "preference")
public class Preference {
	
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")    
    private String UID;        // User ID
	
    @Column(name = "Start_page")    
    private String startPage;        // Start Page
                                     // ??????? Store the page number of the start page
    
    @Column(name = "Favorites")    
    private String favorites;        // Favorite pages
                                     // ??????? Maybe use an arraylist instead or string
    
    
    @Column(name = "Color_preferences")    
    private String colorPreferences;    // Color preferences


    
    
    // Getters and Setters
    
	public String getUID() {
		return UID;
	}


	public void setUID(String uID) {
		UID = uID;
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
