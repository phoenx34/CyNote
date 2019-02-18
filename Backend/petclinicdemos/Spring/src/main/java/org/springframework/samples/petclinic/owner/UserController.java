package org.springframework.samples.petclinic.owner;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


// You write all the functions in the controller's class
// Every method is paired with a path
// The path direct the request from the users to the specific method
public class UserController {


    @Autowired        // @Autowired means that the controller is connected with the database 
    UserRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    // @RequestMapping part below is an example of the rest API
    // "RequestMethod.POST" the post here represent your changing something of the data base 
    // Every Mapping is required with a "Path", it tells the program on when the path is given, run the method
    @RequestMapping(method = RequestMethod.POST, path = "/users/new")
    public String saveUser(User user) {
        usersRepository.save(user);
        return "New User "+ user.getScreenname() + " Saved";
    }

    
    
   /**
    * The method here is to delete user in the database by userID
    * @param userID The input userID to be deleted 
    * @return Whether the user has been deleted 
    * @throws IllegalArgumentException Exception when the input string is null
    */
    @RequestMapping(method = RequestMethod.POST, path = "/users/delete")
    public String deleteUser(String userID) throws IllegalArgumentException {
    	if(userID == null)
    		throw new IllegalArgumentException();      // ????? difference between IllegalArgumentException and NUllPointerException
    	if(userID.trim().length()==0)       //?????? difference between this and the exception
    	{
    		return "UserId cannot be empty, try again";
    	}
        logger.info("Entered into Controller Layer");
        List<User> results = usersRepository.findAll();        // Obtain the list of users 
        User targetUser = null;        // targetUser is the user we are trying to delete 
        int counter = 0;               // to count and make sure the input string actually exists in the list 
    	for(User user : results)
    	{
    		if(user.getUID().equals(userID))
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
    
    
    
    
    
    //"RequestMethod.GET" the Get here represent your not changing something from the data base
    // All your doing is to use the information from the data base to return to the user 
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getAllUsers() {
        logger.info("Entered into Controller Layer");
        List<User> results = usersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users/{userId}")
    public Optional<User> findUserById(@PathVariable("userId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<User> results = usersRepository.findById(id);
        return results;
    }
}
