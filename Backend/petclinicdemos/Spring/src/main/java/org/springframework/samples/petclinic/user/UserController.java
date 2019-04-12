package org.springframework.samples.petclinic.user;

import java.net.URI;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.classEntity.ClassController;
import org.springframework.samples.petclinic.classEntity.ClassRepository;
import org.springframework.samples.petclinic.classEntity.ClEnt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


// You write all the functions in the controller's class
// Every method is paired with a path
// The path direct the request from the users to the specific method
@RestController
public class UserController {


    @Autowired        // @Autowired means that the controller is connected with the database 
    UserRepository usersRepository;
    
    @Autowired
    ClassController classCont;
    
    @Autowired
    ClassRepository classRepo;
    

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    
 
    
    
    
    

   
    /**
     *  This is a login method, it first check if the input userName exist and then check to see if it matches with the password
     *  In the jason request body, gives only the password
     * @param username Obtained from the Jason request link
     * @param password Obtained from the Jason request link
     * @return If the login is successful
     */
    /*@RequestMapping(method = RequestMethod.GET, path = "/usersLogin")
    public String loginWithUsername(@RequestBody String username, @RequestBody String password)throws IllegalArgumentException 
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
    			return "{\"status\":4,\"UID\":" + user.getUID().toString() + "}";  // password mathches with the username 
    		}
    		else
    		{
    			return "{\"status\":5,\"UID\":0}";   //  password doesn't match with the username
    		}
    	}
    	
    }*/
    
    
    
    @RequestMapping(method = RequestMethod.GET, path = "/userLogin/{screenname}/{password}", headers = "Accept=application/json")
    public String loginUserPass(@PathVariable("screenname") String screenname, @PathVariable("password") String password){
        if(screenname == null || screenname.trim().length()==0)
            throw new IllegalArgumentException("The input screename is not valid");
        
        // List<User> results = userService.getUsers();
        List<User> results = usersRepository.findAll();       // list of users 


        for(User user : results)
    	{
    		if(user.getScreenname().equals(screenname))
    		{
    			if(user.getPassword().equals(password)) {
    	            return "{\"status\":4,\"UID\":" + user.getUID().toString() + "}";
    			} else {
    				return "{\"status\":5,\"UID\":0}"; 
    			}
    		} else {
    			if(user == null)
    	            return "{\"status\":3,\"UID\":0}"; 
    		}
    	}
		return null;
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
    @RequestMapping(method = RequestMethod.GET, path = "/users_class/{id}")
    public String getClassList(@PathVariable("id") Integer id) {
    	logger.info("Entered into Controller Layer");
    	logger.info("id: " + id);
    	Optional<User> results = usersRepository.findById(id);
    	logger.info("userid: " + results.get().getUID());
    	if(results.isPresent() == false)
    		return null;
    	User user = results.get();
    	List<ClEnt> classes = user.getClasses();
    	logger.info("size of class list: " + classes.size());
    	//classes.toArray();

    	String result = "{\"classes\":[";

    	for(int i=0; i < classes.size(); i++) {

    	   result += "{\"cid\":\"" + classes.get(i).getId()+"\",";
    	   result += "\"name\":\"" + classes.get(i).getName()+"\"},";

    	}

    	//Removes final comma
    	result = result.replaceAll(", $", "");
    	result += "]}";
    	
    	
    	
    	
    	/*logger.info("size of classes" + classes.size());
    	
    	for(int i=0; i < classes.size(); i++) {
    		classEntity temp = classes.get(i);
    		result.add(temp.getName());
    	}
    	
    	List<classEntity> classes = classRepo.findAll();
    	List<String> result = null;
    	
    	for(classEntity c : classes) {
    		List<User> users = c.getUsers();
    		logger.info("users for class size: " + users.size());
    		for(User use : users) {
    			if(use.getUID().equals(user.getUID())) {
    				result.add(c.getName());
    			}
    		}
    	}*/
		logger.info("string is: " + result); 
		return result;
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/addclass/{uid}/{cid}")
    public boolean addUsertoClass(@PathVariable("uid") Integer uid, @PathVariable("cid") Integer cid) {
		
    	User u = null;
    	 
        List<User> results = usersRepository.findAll();       // list of users 


        for(User user : results)
    	{
    		if(user.getUID().equals(uid))
    		{
    			u = user;
    		}
    	}
        
        if(u == null) {
        	return false;
        }
        
        
    	
    	ClEnt classent = null;
    	
    	List<ClEnt> classes = classRepo.findAll();
    	
    	for(ClEnt classe : classes) {
    		if(classe.getId().equals(cid)) {
    			classent = classe;
    		}
    	}
    	
    	if(classent == null) {
    		return false;
    	}
    	
    	classent.addUser(u);
    	u.addClass(classent);
    	logger.info("classlist size: " + classent.getUsers().size());
    	logger.info("user class list size: " + u.getClasses().size());
    	
    	this.deleteUser(uid);
    	this.createStudent(u);
    	
    	
    	
    	
    	
    	return true;
    	
    }
    
    
    
    
    
  
    /**
     * Return all the users
     * @return all the users 
     */
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getAllUsers() {
        logger.info("Entered into Controller Layer");
        List<User> results = usersRepository.findAll();
        logger.info("Number of Records Fetched: " + results.size());
        return results;
    }

    
    
    





    /**
     * Create a new user
     * @param user The user object obtained from the Jason request
     * @return
     */
    /*@PostMapping("/users/new")
    public String createStudent(@RequestBody User user) {
        /*if(userApplication.usernamelAlreadyExisted(user.getScreenname())==true)
            return "{\"status\":0,\"UID\":0}";
        if(userApplication.emailAlreadyExisted(user.getEmail())==true)
            return "{\"status\":1,\"UID\":0}";*
        User savedUser = usersRepository.save(user);
        return "{\"status\":2,\"UID\":savedUser.getUID().toString()}";
    }*/
    
    @PostMapping("/users") 
    public ResponseEntity<Object> createStudent(@RequestBody User user) { 	
    	User savedUser = usersRepository.save(user);  	
    	URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") 			
    			.buildAndExpand(savedUser.getUID()).toUri();  	
    	return ResponseEntity.created(location).build();  
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
