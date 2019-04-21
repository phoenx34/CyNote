package org.springframework.samples.petclinic.user;

import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.classEntity.ClEnt;
import org.springframework.samples.petclinic.preference.Preference;

/**
 * The User is the object that stores all the information of a user
 * @author Shen Chen
 * @author Marc Issac
 */
@Entity       // @Entitiy means that we are adding a new table 
@Table(name = "user")
public class User {
	
	@ManyToMany(mappedBy = "users")
	private List<ClEnt> classes;
	
	/*@OneToOne
	@JoinColumn(name="preference_PID")
	@RestResource(path = "userPreference", rel="preference")
	private Preference preference;*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")        
    private Integer UID;
    

    @Column(name = "screenname")
    private String screenname;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private String create_time;

    @Column(name = "email")
    private String email;
    
    @Column(name = "type")
    private String type;
    
    /**
     * Default constructor 
     */
    public User() {
    	this.UID = 123456789;
    	this.screenname = "defaultconstructor";
    	this.password = "passssssword";
    	this.create_time = "12:12:12";
    	this.email = "hotmail";
    	this.type = "user";
    }
    
    /**
     * Constructor with all input 
     * @param UID User ID 
     * @param screenname Username
     * @param password Password
     * @param create_time The time of creation 
     * @param email Email of the user 
     * @param type The type of user
     */
    public User(Integer UID, String screenname, String password, String create_time, String email, String type) {
    	this.UID = UID;
    	this.screenname = screenname;
    	this.password = password;
    	this.email = email;
    	this.type = type;
    	this.create_time = create_time;
    }

    
    /**
     * Getter for UID
     * @return UID 
     */
    public Integer getUID() {
        return UID;
    }

    /**
     * Setter for UID 
     * @param UID User ID
     */
    public void setUID(Integer UID) {
    	this.UID = UID;
    }
    
    /**
     * Getter for screenname
     * @return screenname
     */
    public String getScreenname() {
    	return screenname;
    }
    
    /**
     * Setter for screenname
     * @param screenname Screenname of the user 
     */
    public void setScreenname(String screenname) {
    	this.screenname = screenname;
    }
    
    /**
     * Getter for password
     * @return password
     */
    public String getPassword() {
    	return password;
    }
    
    /**
     * Getter for classList
     * @return classes
     */
    public List<ClEnt> getClasses(){
    	return classes;
    }
    
    /**
     * Setter for password
     * @param password password of the user 
     */
    public void setPassword(String password) {
    	this.password = password;
    }
    
    /**
     * Add a class to a user 
     * @param classent the class objcet 
     */
    public void addClass(ClEnt classent) {
    	classes.add(classent);
    }
    
    /**
     * Getter for the create time 
     * @return
     */
    public String getCreate_time() {
    	return create_time;
    }
    
    /**
     * The method check if the user is a new user 
     * @return if the user is new 
     */
    public boolean isNew() {
        return this.UID == null;
    }
    
    
    /**
     * Getter for email 
     * @return email
     */
    public String getEmail() {
    	return email;
    }
    
    /**
     * Setter for email 
     * @param email Email of a user 
     */
    public void setEmail(String email) {
    	this.email = email;
    }
    
    /**
     * Getter for type
     * @return type
     */
    public String getType() {
    	return type;
    }
    
    /**
     * Setter for type 
     * @param type What type of user
     */
    public void setType(String type) {
    	this.type = type;
    }
    
    
    /**
     * This is a toString method of all the variabales of a user
     */
    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getUID())
                .append("new", this.isNew())
                .append("lastName", this.getScreenname())
                .append("firstName", this.getPassword())
                .append("address", this.getEmail())
                .append("address", this.getType())
                .append("telephone", this.getCreate_time()).toString();
    }
}
