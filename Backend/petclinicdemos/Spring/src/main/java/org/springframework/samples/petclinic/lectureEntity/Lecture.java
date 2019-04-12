package org.springframework.samples.petclinic.lectureEntity;

import java.util.HashSet;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.samples.petclinic.classEntity.ClEnt;
import org.springframework.samples.petclinic.notes.Notes;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "lectureEntity")
public class Lecture {
	
	

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "lid", nullable=false) 
	    private Integer Lid; // The id of each one of the lecture
	    
	    
	   
	  @ElementCollection
	    @CollectionTable(name = "lecture_shoutout_history", joinColumns = @JoinColumn(name = "LectureID"))
	    @Column(name = "shoutout_history")
	    private Set<String> shoutout_history = new HashSet<>();   // stores all the chat history as string in the set 

	// This is a many to one relationship with the class
	    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	    @JoinColumn(name = "ClEnt_id")
	    @JsonIgnore
	    private ClEnt classentity;
	    
	    @OneToMany(mappedBy="lecture")
	    private List<Notes> notes;
	    
	   
	    
		 /* @Column(name = "leccid", nullable=false)
		  private Integer leccid;*/
	  
	  
	 // Gettters and Setters
	public Integer getLid() {
		return Lid;
	}
	
	public ClEnt getClassEnt() {
		return this.classentity;
	}
	
	public void setClassEnt(ClEnt classentity) {
		this.classentity = classentity;
	}
	
	
	
	public List<Notes> getNotes() {
		return notes;
	}



	public void setLid(Integer lid) {
		Lid = lid;
	}



	public Set<String> getShoutout_history() {
		return shoutout_history;
	}



	public void setShoutout_history(Set<String> shoutout_history) {
		this.shoutout_history = shoutout_history;
	}
	
	
	// adding a message in the  the shoutout list 
	public void addToShoutoutList(String givenMessage)
	{
		shoutout_history.add(givenMessage);
	}
	
	
	
	
	
}
