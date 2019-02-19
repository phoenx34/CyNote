package org.springframework.samples.petclinic.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    UserApplication userApplication;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    
    
    // !!!!!!!!! it is possible that this method is not valid 
    // @RequestMapping part below is an example of the rest API
    // "RequestMethod.POST" the post here represent your changing something of the data base 
    // Every Mapping is required with a "Path", it tells the program on when the path is given, run the method
    /**
     * The method is to create a new user in the database 
     * @param user The user is obtained from the jason request string 
     * @return Check if the email and username are already been used, if not, create the new user in the database
     */
    /*@RequestMapping(method = RequestMethod.POST, path = "/users/new")
    public String saveUser(@RequestBody User user) {
    	/*if(this.emailAlreadyExisted(user.getEmail()).equals("The email already existed"))
    		return "The email already existed, please log in or use a different email to create the account";
    	else if(this.usernamelAlreadyExisted(user.getScreenname()).equals("Username already exited, try a different ones"))
    		return "Username already exited, try a different ones";
    	else
    	{
    		 usersRepository.save(user);
    	     return "New User "+ user.getScreenname() + " Saved";
    	
    }*/

    
    // parse the jason string request into an object
    // check if the email is valid
    // check if the user name matches the email
    
    /**
     *  This is a login method, it first check if the input userName exist and then check to see if it matches with the password
     * @param username Obtained from the Jason request link
     * @param password Obtained from the Jason request link
     * @return If the login is successful
     *
    @RequestMapping(method = RequestMethod.GET, path = "/users/login/{userName}/{passWord}")
    public String loginWithUsername(@PathVariable("userName") String username, @PathVariable("passWord") String password)throws IllegalArgumentException {
    	if(this.usernamelAlreadyExisted(username).equals("Username does not exist"))
    		return "The username does not exist, check the spelling";
    	User inputUser = this.findUserByUsername(username);
    	if(inputUser.getPassword().equals(password))
    	{
    		return "Success";
    	}
    	else
    	{
    		return "Incorrect password, try again";
    	}
    }
    */
    
    
    
    
   
    
    // check if the email is already in the database
    // given an email address, and check if the email is already in the system
    //  ???????? Do we want the input to be a jason request or just the string emialAddress
    /**
     * This method is to check if the email already exist in the database 
     * @param emailAddress This is given as a string at the moment 
     * @return  To see if the email already exist in the database 
     * @throws IllegalArgumentException When the input is not even valid 
     */
    /*@RequestMapping(method = RequestMethod.GET, path = "/users/emailAvalibility/{emailAddress}")
    public String emailAlreadyExisted(@PathVariable String emailAddress) throws IllegalArgumentException {
    	if(emailAddress == null || emailAddress.trim().length()==0)
    		throw new IllegalArgumentException("The input email address is not valid");
        logger.info("Entered into Controller Layer");
        List<User> results = usersRepository.findAll();       // list of users 
        for(User user : results)
    	{
    		if(user.getEmail().equals(emailAddress))
    		{
    			return "The email already existed";
    		}
    	}
        return "The email does not exist";  
    }
    
    
    
    
    
    // check if the email is already in the database
    // given an email address, and check if the email is already in the system
    //  ???????? Do we want the input to be a jason request or just the string emialAddress
    /**
     * This method is to check if the username already exist in the database 
     * @param userName This is given as a string at the moment
     * @return To see if the username already exist in the database
     * @throws IllegalArgumentException When the input is not even valid
     *
    @RequestMapping(method = RequestMethod.GET, path = "/users/userNameAvalibility/{userName}")
    public String usernamelAlreadyExisted(@PathVariable String userName) throws IllegalArgumentException {
    	if(userName == null || userName.trim().length()==0)
    		throw new IllegalArgumentException("The input email address is not valid");
        logger.info("Entered into Controller Layer");
        List<User> results = usersRepository.findAll();       // list of users 
        for(User user : results)
    	{
    		if(user.getEmail().equals(userName))
    		{
    			return "Username already exited, try a different ones";
    		}
    	}
        return "Username does not exist";  
    }
    
    
    
    
    
    
   /**
    * The method here is to delete user in the database by userID
    * @param userID The input userID to be deleted 
    * @return Whether the user has been deleted 
    * @throws IllegalArgumentException Exception when the input string is null
    */
    @RequestMapping(method = RequestMethod.POST, path = "/users/{userID}")
    public String deleteUser(@PathVariable String userID) throws IllegalArgumentException {
    	if(userID == null)
    		throw new IllegalArgumentException();      // ????? difference between IllegalArgumentException and NUllPointerException
    	if(userID.trim().length()==0)       //?????? difference between this and the exception
    	{
    		return "UserId cannot be empty, try again";
    	}
        logger.info("Entered into Controller Layer");
        List<User> results = (List<User>) usersRepository.findAll();        // Obtain the list of users 
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
        List<User> results = (List<User>) usersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }
    
    /*@PostMapping("/users/new")
    User post(@RequestBody User user) {
    	return userApplication.create(user);
    }*/
    
    @PostMapping("/users") 
    public ResponseEntity<Object> createStudent(@RequestBody User user) { 	
    	User savedUser = usersRepository.save(user);  	
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") 			
    			.buildAndExpand(savedUser.getUID()).toUri();  	
    	return ResponseEntity.created(location).build();  
    }
    
    
    

    @RequestMapping(method = RequestMethod.GET, path = "/users/{userId}")
    public Optional<User> findUserById(@PathVariable("userId") Integer id) {
        logger.info("Entered into Controller Layer");
        Optional<User> results = usersRepository.findById(id);
        return results;
    }
    
    //TODO
    // @PathVariable means I am obtaining the string from the jason request link
    // @RequestBody means I am obtaining the stuff from the jason request string  
    /**
     * The method is to find and return user by username
     * @param userName The input taken from the jason request link
     * @return The user object found from the database, which means it could be null if it does not exits 
     */
    /*@RequestMapping(method = RequestMethod.GET, path = "/useName/{userName}")
    public User findUserByUsername(@PathVariable String userName) {
    	logger.info("Entered into Controller Layer");
    	Optional<User> results = usersRepository.findUserByUsername(userName);
    	return this.findUserByUsername(userName);		//Changed by SG, was giving error before, incorrect method use
    	
    	//return usersRepository.findByUsername(userName).get();
    }*/

    
}
