package lectureEntity;
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


// have to have a lecture first in the 
public class LectureController {

	@Autowired
	LectureRepository lectureRepository;

	private final Logger logger = LoggerFactory.getLogger(LectureController.class);

	
	
	// !!!!!!!!!! make sure when posting a new lecture make a list of lectureChat
	
	
	
}
