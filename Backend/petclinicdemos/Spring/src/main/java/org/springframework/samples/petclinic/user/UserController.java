package org.springframework.samples.petclinic.user;

import java.net.URI;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.classEntity.classEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


// You write all the functions in the controller's class
// Every method is paired with a path
// The path direct the request from the users to the specific method
@RestController
public class UserController {


    @Autowired        // @Autowired means that the controller is connected with the database 
    UserRepository usersRepository;
    UserService userApplication;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    
 
    
    
    
    

   
    /**
     *  This is a login method, it first check if the input userName exist and then check to see if it matches with the password
     *  In the jason request body, gives only the password
     * @param username Obtained from the Jason request link
     * @param password Obtained from the Jason request link
     * @return If the login is successful
     */
    @RequestMapping(method = RequestMethod.GET, path = "/usersLogin/{userName}")
    public String loginWithUsername(@PathVariable("userName") String username, @RequestBody String password)throws IllegalArgumentException 
    {
    	if(username == null || username.trim().length()==0)
    		throw new IllegalArgumentException("The input email address is not valid");
    	User user = userApplication.findUserFromUsername(username);
    	if(user==null)  // user does not exist
    	{
    		return "{\"status\":3,\"UID\":0}";    // user does not exist 
    	}
    	else
    	{
    		if(user.getPassword().equals(password))
    		{
    			return "{\"status\":4,\"UID\":user.getUID().toString()}";  // password mathches with the username 
    		}
    		else
    		{
    			return "{\"status\":5,\"UID\":0}";   //  password doesn't match with the username
    		}
    	}
    	
    }
    
    
    
    
    

   /**
    * The method here is to delete user in the database by userID
    * @param userID The input userID to be deleted 
    * @return Whether the user has been deleted 
    * @throws IllegalArgumentException Exception when the input string is null
    */
    @RequestMapping(method = RequestMethod.POST, path = "/users/{userID}/del")
    public String deleteUser(@PathVariable Integer userID) {
    	if(userID == null)
    		throw new IllegalArgumentException();      // ????? difference between IllegalArgumentException and NUllPointerException         
        logger.info("Entered into Controller Layer");
        List<User> results = (List<User>) usersRepository.findAll();        // Obtain the list of users 	
        User targetUser = null;        // targetUser is the user we are trying to delete 
        int counter = 0;               // to count and make sure the input string actually exists in the list 
    	for(User user : results)
    	{
    		if(user.getUID()==userID)
    		{
    			targetUser = user;
    			break;
    		}
    		else
    		{
    			counter ++;	     // only count when it is not matched 
    		}
    	}
    	if(counter==results.size())      // if the userID enter is not in the list, prompt the user again
    	{
    		return "The input userID does not exist, please check and make sure you have the correct userID and try again";
    		// ?????????? How to immediate ask the user to try again 
    	}
    	else
    	{
    		usersRepository.delete(targetUser);
            return "User "+ userID + " Deleted";

    	}
    }
    
    
    
    
    
    /**
     * Return the class list of each user
     * @param id Return a list of classes 
     * @return All the classes 
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<String> getClassList(Integer id) {
    	logger.info("Entered into Controller Layer");
    	Optional<User> results = usersRepository.findById(id);
    	if(results.isPresent() == false)
    		return null;
    	User user = results.get();
    	List<classEntity> classes = user.getClasses();
    	classes.toArray();
    	List<String> result = null;
    	for(int i=0; i < classes.size(); i++) {
    		classEntity temp = classes.get(i);
    		result.add(temp.getName());
    	}
		return result;
    }
    
    
    
    
    
  
    /**
     * Return all the users
     * @return all the users 
     */
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getAllUsers() {
        logger.info("Entered into Controller Layer");
        List<User> results = (List<User>) usersRepository.findAll();
        logger.info("Number of Records Fetched: " + results.size());
        return results;
    }

    
    
    





    /**
     * Create a new user
     * @param user The user object obtained from the Jason request
     * @return
     */
    @PostMapping("/users/new")
    public String createStudent(@RequestBody User user) {
        if(userApplication.usernamelAlreadyExisted(user.getScreenname())==true)
            return "{\"status\":0,\"UID\":0}";
        if(userApplication.emailAlreadyExisted(user.getEmail())==true)
            return "{\"status\":1,\"UID\":0}";
        User savedUser = usersRepository.save(user);
        return "{\"status\":2,\"UID\":savedUser.getUID().toString()}";
    }


    
    /**
     * Return the user with given userID
     * @param id Return the user with the given user ID
     * @return Return a user with a given
     */
    @RequestMapping(method = RequestMethod.GET, path = "/users/{userId}")
    public Optional<User> findUserById(@PathVariable("userId") Integer id) {
        logger.info("Entered into Controller Layer");
        Optional<User> results = usersRepository.findById(id);
        return results;
    }
    
    
    

    
}
