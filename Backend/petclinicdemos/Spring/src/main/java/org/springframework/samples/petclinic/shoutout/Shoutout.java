package org.springframework.samples.petclinic.shoutout;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.samples.petclinic.classEntity.classEntity;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */



@Entity
@Table(name = "shoutout")
public class Shoutout {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SID")    
    private int SID;        // Shoutout ID
    

    
    @Column(name = "Lecture_number")    
    private String lecNum;        // Lecture number

    
    @Column(name = "Content")    
    private String content;        // content of each shoutout

    
    @Column(name = "Class_CID")    
    private int class_CID;        // The class ID, how we identify which class
    
    
    // This is a many to one relationship with the class
    @ManyToOne
    @JoinColumn(name="Class_CID")
    private classEntity classentity;
    
    
    
    
    
    
    // Getters and Setters
   

	public int getSID() {
		return SID;
	}


	public void setSID(int sID) {
		SID = sID;
	}


	public String getLecNum() {
		return lecNum;
	}


	public void setLecNum(String lecNum) {
		this.lecNum = lecNum;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getClass_CID() {
		return class_CID;
	}


	public void setClass_CID(int class_CID) {
		this.class_CID = class_CID;
	}

    
    
    

    


	
	
    
    
    
    
	
}
