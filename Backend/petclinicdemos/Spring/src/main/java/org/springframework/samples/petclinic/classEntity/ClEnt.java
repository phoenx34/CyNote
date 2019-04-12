
package org.springframework.samples.petclinic.classEntity;
 
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.samples.petclinic.lectureEntity.Lecture;
import org.springframework.samples.petclinic.user.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

// CLEnt means a class entity
@Entity
@Table(name = "ClEnt")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class ClEnt implements Serializable{
  private static final long serialVersionUID = 1L;
  
  
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(name = "name")
  private String name;
  
    @OneToMany(mappedBy = "ClEnt", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Lecture> lectures;
  
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_has_Class",
    		joinColumns = @JoinColumn(name = "ClEnt_id", referencedColumnName="id"),
    		inverseJoinColumns = @JoinColumn(name = "user_UID",
    		referencedColumnName = "UID"))
    private List<User> users;
    
    
    
    
  public ClEnt() {}
  
  public ClEnt(String name) {
    this.name = name;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  public Long getId() {
    return this.id;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  public String getName() {
    return this.name;
  }
  
  
  public void setLectures(Set<Lecture> lectures) {
    this.lectures = lectures;
  }
  public Set<Lecture> getLectures(){
    return this.lectures;
  }
  
	public List<User> getUsers() {
		return users;
	}
	public void addUser(User u) {
		users.add(u);
	}
  
  
  
  
  
  
}










/*

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

*/
