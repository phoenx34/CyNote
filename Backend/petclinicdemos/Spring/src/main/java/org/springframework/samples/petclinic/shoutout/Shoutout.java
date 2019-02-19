package org.springframework.samples.petclinic.shoutout;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    @Column(name = "Class number")    
    @NotFound(action = NotFoundAction.IGNORE)
    private String classNum;        // Class number
    
    
    @Column(name = "Lecture number")    
    @NotFound(action = NotFoundAction.IGNORE)
    private String lecNum;        // Lecture number

    @Column(name = "SID")    
    @NotFound(action = NotFoundAction.IGNORE)
    private String SID;        // Shoutout ID
    
    @Column(name = "Content")    
    @NotFound(action = NotFoundAction.IGNORE)
    private String content;        // content of each shoutout

    
    
    
    // Getters and Setters
    
	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public String getLecNum() {
		return lecNum;
	}

	public void setLecNum(String lecNum) {
		this.lecNum = lecNum;
	}

	public String getSID() {
		return SID;
	}

	public void setSID(String sID) {
		SID = sID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	
	
	// toString Method
	@Override
	public String toString() {
		return "Shoutout [classNum=" + classNum + ", lecNum=" + lecNum + ", SID=" + SID + ", content=" + content + "]";
	}
    
    
	
	
    
    
    
    
	
}
