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
 * @author Shen Chen
 */



@Entity
@Table(name = "class")
public class classEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")    
    @NotFound(action = NotFoundAction.IGNORE)
    private String UID;        // User ID
	
    
    @Column(name = "NID")
    @NotFound(action = NotFoundAction.IGNORE)
    private String NID;      // Note ID
    
    
    @Column(name = "SID")
    @NotFound(action = NotFoundAction.IGNORE)
    private String SID;      // Shoutout ID
    
    @Column(name = "TID")
    @NotFound(action = NotFoundAction.IGNORE)
    private String TID;      // TextBook ID
    
    @Column(name = "class number")
    @NotFound(action = NotFoundAction.IGNORE)
    private String classNum;      // Class number
	
    @Column(name = "lecture number")
    @NotFound(action = NotFoundAction.IGNORE)
    private String lecNum;      // Lecture number 
    
    
    @Column(name = "Lecture date")
    @NotFound(action = NotFoundAction.IGNORE)
    private String LecDate;      // Lecture date




    // Getters and Setters below
   
	public String getUID() {
		return UID;
	}


	public void setUID(String uID) {
		UID = uID;
	}


	public String getNID() {
		return NID;
	}


	public void setNID(String nID) {
		NID = nID;
	}


	public String getSID() {
		return SID;
	}


	public void setSID(String sID) {
		SID = sID;
	}


	public String getTID() {
		return TID;
	}


	public void setTID(String tID) {
		TID = tID;
	}


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


	public String getLecDate() {
		return LecDate;
	}


	public void setLecDate(String lecDate) {
		LecDate = lecDate;
	}


	
	// toString method
	@Override
	public String toString() {
		return "Class [UserID=" + UID + ", ShoutoutID=" + SID + ", TID=" + TID + ", ClassNum=" + classNum + ", LectureDate=" + this.getLecDate()
				+ ", LectureNum=" + lecNum + ", NoteID=" + this.getNID() + "]";
	}
	
	
}
