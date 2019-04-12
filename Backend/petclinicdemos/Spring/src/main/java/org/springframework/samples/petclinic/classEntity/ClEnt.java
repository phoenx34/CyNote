package org.springframework.samples.petclinic.classEntity;

import java.util.List;


import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.lectureEntity.Lecture;
import org.springframework.samples.petclinic.shoutout.Shoutout;
import org.springframework.samples.petclinic.textbook.Textbook;
import org.springframework.samples.petclinic.user.User;

/**
 * 
 * @author Shen Chen and Marc Isaac
 */



@Entity
@Table(name = "ClEnt")
public class ClEnt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable=false)    
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer id;        // User ID
	
    
    @Column(name = "Name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String Name;      // Note ID
    
    @OneToMany(mappedBy="clEnt")
    private List<Textbook> textbook;
    
    @OneToMany(mappedBy="clEnt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lecture> lectures;
    
    @OneToMany(mappedBy="clEnt")
    private List<Shoutout> shoutout;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_has_Class",
    		joinColumns = @JoinColumn(name = "clEnt_id", referencedColumnName="id"),
    		inverseJoinColumns = @JoinColumn(name = "user_UID",
    		referencedColumnName = "UID"))
    private List<User> users;


    // Getters and Setters below
   
	public Integer getCID() {
		return id;
	}
	
	public List<Textbook> getTextbooks(){
		return textbook;
	}
	
	public List<Shoutout> getShoutouts() {
		return shoutout;
	}
	
	public List<Lecture> getLecture() {
		return lectures;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}
	
	public void addUser(User u) {
		users.add(u);
	}


	public void setId(Integer Id) {
		id = Id;
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
		return "Class Name=" + Name + ", Class ID=" + id;
	}
	
	
}
