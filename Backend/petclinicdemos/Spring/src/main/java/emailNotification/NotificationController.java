package emailNotification;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.classEntity.ClassRepository;
import org.springframework.samples.petclinic.classEntity.classEntity;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@RestController
public class NotificationController {

	@Autowired
	private SmtpMailSender smtpMailSender;
	
    @Autowired
    ClassRepository classRepository;

	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	// !!!!!!!!!!!!! This needs to be change into
	// "send-mail/{classNum}"
	// With the given classNum, go into the database and loop through all the student and send email to all of them
	@RequestMapping(method = RequestMethod.GET, path = "/send-mail/{id}")
	public void sendMailNotification(@PathVariable("id") Integer id) throws IllegalArgumentException
	{
	    	logger.info("The method started");
	    	logger.info("The given classID from html path is: " + id);
	    	
	    	
	    	Optional<classEntity> result = classRepository.findById(id);
	    	logger.info("The actual classID for the class is: " + result.get().getCID());
	    	logger.info("The actual className for the class is: " + result.get().getName());
	    	
	    	// What do you do when the class is not found 
	    	if(result.isPresent() == false)
	    		throw new IllegalArgumentException("The class can not be found");
	    		
	    	classEntity whichClass = result.get();
	    	
	    	List<User> studentInTheClass = whichClass.getUsers();
	    	logger.info("The size of class is: " + studentInTheClass.size());
	    	
	    	int sizeOfClass = studentInTheClass.size();
	    	
	    	for(User student : studentInTheClass)
	    	{
	    		String email = student.getEmail();
	    		try {
					smtpMailSender.send(email, "Message from CyNote", "Checkout the app, you have a new message");
				} catch (MessagingException e) {
					logger.info("Ahhhhhhhhhh, the send email does not work");
					e.printStackTrace();
				}
	    	}
		
	}
	
	
}
