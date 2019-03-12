package org.springframework.samples.petclinic.classEntity;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.shoutout.Shoutout;
import org.springframework.samples.petclinic.textbook.Textbook;
import org.springframework.samples.petclinic.user.User;

/**
 * 
 * @author Shen Chen and Marc Isaac
 */



@Entity
@Table(name = "classent")
public class classEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CID", nullable=false)    
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer CID;        // User ID
	
    
    @Column(name = "Name", nullable=false)
    @NotFound(action = NotFoundAction.IGNORE)
    private String Name;      // Note ID
    
    @OneToMany(mappedBy="classentity")
    private List<Textbook> textbook;
    
    @OneToMany(mappedBy="classentity")
    private List<Shoutout> shoutout;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_has_Class",
    		joinColumns = @JoinColumn(name = "Class_CID", referencedColumnName="CID"),
    		inverseJoinColumns = @JoinColumn(name = "user_UID",
    		referencedColumnName = "UID"))
    private List<User> users;


    // Getters and Setters below
   
	public Integer getCID() {
		return CID;
	}
	
	public List<Textbook> getTextbooks(){
		return textbook;
	}
	
	public List<Shoutout> getShoutouts() {
		return shoutout;
	}
	
	public List<User> getUsers() {
		return users;
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
