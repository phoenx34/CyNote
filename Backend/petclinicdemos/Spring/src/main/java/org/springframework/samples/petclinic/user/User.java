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
import org.springframework.samples.petclinic.classEntity.classEntity;
import org.springframework.samples.petclinic.preference.Preference;

/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */

@Entity       // @Entitiy means that we are adding a new table 
@Table(name = "user")
public class User {
	
	@ManyToMany(mappedBy = "users")
	private List<classEntity> classes;
	
	@OneToOne
	@JoinColumn(name="preference_PID")
	//@RestResource(path = "userPreference", rel="preference")
	private Preference preference;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    
    public User() {
    	this.UID = 123456789;
    	this.screenname = "defaultconstructor";
    	this.password = "passssssword";
    	this.create_time = "12:12:12";
    	this.email = "hotmail";
    	this.type = "user";
    }
    
    public User(Integer UID, String screenname, String password, String create_time, String email, String type) {
    	this.UID = UID;
    	this.screenname = screenname;
    	this.password = password;
    	this.email = email;
    	this.type = type;
    	this.create_time = create_time;
    }

    
    
    public Integer getUID() {
        return UID;
    }

    public void setUID(Integer UID) {
    	this.UID = UID;
    }
    
    public String getScreenname() {
    	return screenname;
    }
    
    public void setScreenname(String screenname) {
    	this.screenname = screenname;
    }
    
    public String getPassword() {
    	return password;
    }
    
    public void setPassword(String password) {
    	this.password = password;
    }
    
    public String getCreate_time() {
    	return create_time;
    }
    
    public boolean isNew() {
        return this.UID == null;
    }
    
    
    public String getEmail() {
    	return email;
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getType() {
    	return screenname;
    }
    
    public void setType(String type) {
    	this.type = type;
    }
    
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
