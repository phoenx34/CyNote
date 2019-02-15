package org.springframework.samples.petclinic.owner;

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

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")
    @NotFound(action = NotFoundAction.IGNORE)
    private String UID;

    @Column(name = "screenname")
    @NotFound(action = NotFoundAction.IGNORE)
    private String screenname;

    @Column(name = "password")
    @NotFound(action = NotFoundAction.IGNORE)
    private String password;

    @Column(name = "create_time")
    @NotFound(action = NotFoundAction.IGNORE)
    private String create_time;

    @Column(name = "email")
    @NotFound(action = NotFoundAction.IGNORE)
    private String email;
    
    @Column(name = "type")
    @NotFound(action = NotFoundAction.IGNORE)
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
    	return screenname;
    }
    
    public boolean isNew() {
        return this.UID == null;
    }
    
    public void setCreate_time(String create_time) {
    	this.create_time = create_time;
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
