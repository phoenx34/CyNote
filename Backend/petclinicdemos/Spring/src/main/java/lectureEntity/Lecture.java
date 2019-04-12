package lectureEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.samples.petclinic.classEntity.classEntity;
import org.springframework.samples.petclinic.notes.Notes;

@Entity
@Table(name = "LectureEntity")
public class Lecture {

	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "lid", nullable=false) 
	    private Integer Lid;   // The id of each one of the lecture
	    
	    
	   
	  @ElementCollection
	    @CollectionTable(name = "lecture_shoutout_history", joinColumns = @JoinColumn(name = "LectureID"))
	    @Column(name = "shoutout_history")
	    private Set<String> shoutout_history = new HashSet<>();   // stores all the chat history as string in the set 

	// This is a many to one relationship with the class
	    @ManyToOne
	    @JoinColumn(name="Class_CID")
	    private classEntity classentity;
	    
	    @OneToMany(mappedBy="lecture")
	    private List<Notes> notes;
	  
	  
	 // Gettters and Setters
	public Integer getLid() {
		return Lid;
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