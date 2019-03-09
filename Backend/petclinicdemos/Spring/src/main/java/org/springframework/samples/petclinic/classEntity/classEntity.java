package org.springframework.samples.petclinic.classEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

/**
 * 
 * @author Shen Chen and Marc Isaac
 */



@Entity
@Table(name = "Class")
public class classEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CID")    
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer CID;        // User ID
	
    
    @Column(name = "Name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String Name;      // Note ID
    



    // Getters and Setters below
   
	public Integer getCID() {
		return CID;
	}


	public void setUID(Integer cID) {
		CID = cID;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}


	
	// toString method
	@Override
	public String toString() {
		return "Class Name=" + Name + ", Class ID=" + CID;
	}
	
	
}
