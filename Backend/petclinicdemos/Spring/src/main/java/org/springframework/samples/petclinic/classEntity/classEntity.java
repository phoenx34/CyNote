package org.springframework.samples.petclinic.classEntity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.shoutout.Shoutout;
import org.springframework.samples.petclinic.textbook.Textbook;

/**
 * 
 * @author Shen Chen and Marc Isaac
 */



@Entity
@Table(name = "Class")
public class classEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CID", nullable=false)    
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer CID;        // User ID
	
    
    @Column(name = "Name", nullable=false)
    @NotFound(action = NotFoundAction.IGNORE)
    private String Name;      // Note ID
    
    @OneToMany(mappedBy="CID")
    private List<Textbook> textbooks;
    
    @OneToMany(mappedBy="CID")
    private List<Shoutout> shoutout;


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
