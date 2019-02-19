package org.springframework.samples.petclinic.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;


/**
 * 
 * @author Shen Chen
 * @author Marc Issac
 */

@Entity       // @Entitiy means that we are adding a new table 
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")        
    private String UID;

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

    
    
    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
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
