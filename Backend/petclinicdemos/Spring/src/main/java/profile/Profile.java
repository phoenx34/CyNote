package profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * This is a profile that contain the info of each user
 * one user one profile
 * one profile one user 
 * one to one realtionship with the user 
 * @author Shen Chen 
 *
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true) // ??????????????? should we even make a table for it???
public class Profile implements Serializable {
 
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	
    @Column(name = "UID")    
    @NotFound(action = NotFoundAction.IGNORE)
    private String UID;        
	
    @Column(name = "ClassList")   // this is a list of classes a student attend, including their role 
    @NotFound(action = NotFoundAction.IGNORE)
    private String[][] classList;

    
    
	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String[][] getClassList() {
		return classList;
	}

	public void setClassList(String[][] classList) {
		this.classList = classList;
	}  
	
    
    
    
}
