package profile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class ProfileController {
	
	@Autowired 
	private UserRepository userRepository; 
	
	@Autowired
	private ProfileRepository profileRepository;
	
	
	/**
	 * Method returns all the profiles of the entire database
	 * With the given /profile path 
	 * @return All the profile
	 */
	@RequestMapping(method= RequestMethod.GET, path = "/profile")
	public List<Profile> getAllProfile(){
		List<Profile> profile =  (List<Profile>) profileRepository.findAll();
		return profile;
	}
	
	
	// ???????????? what should be the return type
	/**
	 * This method returns all the classes a user has 
	 * @param Uid Given user ID 
	 * @return The list of classes
	 */
	@RequestMapping(method= RequestMethod.GET, path ="/profile/{UID}")
	public String[] getUserForProfile(@PathVariable String UID){
		if (userRepository.findById(UID).isPresent())
			return (String[]) profileRepository.findAllById(UID);
		else
			return null;        // ??????????????? can you return null for jason request
	}
	
	
	
//	
//	@RequestMapping(method= RequestMethod.GET, path ="/profile/{username}/users")
//	public Map<String, Object> getUserForProfile_2(@PathVariable String username){
//		if (userRepository.findByUsername(username).isPresent())
//			return profileRepository.findByUsername(username).get().getUser().toProfileDTO();
//		else
//			return new HashMap<String, Object>();
//	}
	
	
	
	
	
}
