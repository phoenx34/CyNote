package org.springframework.samples.petclinic.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.classEntity.ClEnt;
import org.springframework.samples.petclinic.classEntity.ClassController;
import org.springframework.samples.petclinic.classEntity.ClassRepository;
import org.springframework.stereotype.Service;



@Service
public class UserService {

		@Autowired       
	    UserRepository usersRepository;
	    
	    @Autowired
	    ClassController classCont;
	    
	    @Autowired
	    ClassRepository classRepo;
	    
	    @Autowired
	    UserController  userController;
	    
	    
	    
	    private final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	
	    
	    
	    
	    
	    
	    public boolean addOneUsertoClass(Integer uid,Long cid)
	    {
	    	User u = null;
	    	 
	        List<User> results = usersRepository.findAll();       // list of users 

	        logger.info("The nuber of users: " + results.size());
	        

	        for(User user : results)
	    	{
	    		if(user.getUID().equals(uid))
	    		{
	    			u = user;
	    		}
	    	}
	        
	        boolean a = u == null;
	        logger.info("Is the user null: " + a);

	        
	        
	        if(u == null) {
	        	return false;
	        }
	        
	        
	    	
	    	ClEnt classent = null;
	    	
	    	List<ClEnt> classes = classRepo.findAll();
	        logger.info("The nuber of classes: " + classes.size());

	    	for(ClEnt classe : classes) {
	    		if(classe.getId().equals(cid)) {
	    			classent = classe;
	    		}
	    	}
	    	
	    	   
	        boolean b = classent == null;
	        logger.info("Is the user null: " + b);
	    	
	    	if(classent == null) {
	    		return false;
	    	}
	    	classent.addUser(u);
	    	u.addClass(classent);
	    	logger.info("classlist size: " + classent.getUsers().size());
	    	logger.info("user class list size: " + u.getClasses().size());
	    	userController.deleteUser(uid);
	    	userController.createStudent(u);
	    	return true;
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
}
