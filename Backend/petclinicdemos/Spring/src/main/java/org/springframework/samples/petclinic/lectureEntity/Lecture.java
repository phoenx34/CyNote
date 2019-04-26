
package org.springframework.samples.petclinic.lectureEntity;


import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.samples.petclinic.classEntity.ClEnt;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
 
/**
 * Lecture means a lecture entity with all the information of a lecture 
 * @author Shen Chen
 * @author Marc Issac
 *
 */
@Entity
@Table(name = "Lecture")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Lecture implements Serializable{
  private static final long serialVersionUID = 1L;
  
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  
  
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "lecture_shoutout_history", joinColumns = @JoinColumn(name = "LectureID"))
  @Column(name = "shoutout_message")
  private List<String> shoutout_history = new LinkedList<String>(); 
//  private List<String> shoutout_history;   // stores all the chat history as string in the set
 
  
  
  //Also need to add the note to this  
  
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ClEnt_id", nullable = false)
    @JsonBackReference
    private ClEnt ClEnt;
  
  
  /**
   * Default construcot of the lecture class 
   */
  public Lecture() {}
  
  
  /**
   * Setter for ID 
   * @param id The lecture ID 
   */
  public void setId(Long id) {
    this.id = id;
  }
  
  /**
   * Getter for ID 
   * @return The Id
   */
  public Long getId() {
    return this.id;
  }
  
 
  
  /**
   * Setter for class entity
   * @param clEnt The input class entity
   */
  public void setClEnt(ClEnt clEnt) {
    this.ClEnt = clEnt;
  }
  
  /**
   * Getter for class entity 
   * @return null since we don't really need this information
   */
  public ClEnt getClEnt() {
    //return this.ClEnt;
	  return null;
  }


  /**
   * Getter for chat history 
   * @return The chat history 
   */
public List<String> getShoutout_history() {
	return shoutout_history;
}

/**
 * Add a chat message from the lecture's shout out section to the chat history 
 * @param message The chat message 
 */
public void addShoutout_TOLIst(String message)
{
	shoutout_history.add(message);
}


/**
 * Setter for the shout out history 
 * @param shoutout_history The given shout out history 
 */
public void setShoutout_history(List<String> shoutout_history) {
	this.shoutout_history = shoutout_history;
}
  
  
  
  
  
  
}

































//package org.springframework.samples.petclinic.lectureEntity;
//
//import java.util.HashSet;
//
//import java.util.List;
//import java.util.Set;
//
//import javax.persistence.*;
//
//import org.hibernate.annotations.NotFound;
//import org.hibernate.annotations.NotFoundAction;
//import org.springframework.samples.petclinic.classEntity.ClEnt;
//import org.springframework.samples.petclinic.notes.Notes;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//@Entity
//@Table(name = "lectureEntity")
//public class Lecture {
//	
//	
//
//	  @Id
//	    @GeneratedValue(strategy = GenerationType.IDENTITY)
//	    @Column(name = "lid", nullable=false) 
//	    private Integer Lid; // The id of each one of the lecture
//	    
//	    
//	   
//	  @ElementCollection
//	    @CollectionTable(name = "lecture_shoutout_history", joinColumns = @JoinColumn(name = "LectureID"))
//	    @Column(name = "shoutout_history")
//	    private Set<String> shoutout_history = new HashSet<>();   // stores all the chat history as string in the set 
//
//	// This is a many to one relationship with the class
//	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//	    @JoinColumn(name = "ClEnt_id")
//	    @JsonIgnore
//	    private ClEnt classentity;
//	    
//	    @OneToMany(mappedBy="lecture")
//	    private List<Notes> notes;
//	    
//	   
//	    
//		 /* @Column(name = "leccid", nullable=false)
//		  private Integer leccid;*/
//	  
//	  
//	 // Gettters and Setters
//	public Integer getLid() {
//		return Lid;
//	}
//	
//	public ClEnt getClassEnt() {
//		return this.classentity;
//	}
//	
//	public void setClassEnt(ClEnt classentity) {
//		this.classentity = classentity;
//	}
//	
//	
//	
//	public List<Notes> getNotes() {
//		return notes;
//	}
//
//
//
//	public void setLid(Integer lid) {
//		Lid = lid;
//	}
//
//
//
//	public Set<String> getShoutout_history() {
//		return shoutout_history;
//	}
//
//
//
//	public void setShoutout_history(Set<String> shoutout_history) {
//		this.shoutout_history = shoutout_history;
//	}
//	
//	
//	// adding a message in the  the shoutout list 
//	public void addToShoutoutList(String givenMessage)
//	{
//		shoutout_history.add(givenMessage);
//	}
//	
//	
//	
//	
//	
//}
