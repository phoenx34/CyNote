package lecture_chatHistory;

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






public class ChatHistoryController {

	
	@Autowired
	ChatHistoryRepository chatHistoryRepository;
	
	private final Logger logger = LoggerFactory.getLogger(ChatHistoryController.class);
	
    @RequestMapping(method = RequestMethod.GET, path = "/classent")
    public List<ChatHistory> getAllClasses() {
        logger.info("in the getAllClasses method");
        List<ChatHistory> results = (List<ChatHistory>) chatHistoryRepository.findAll();
        logger.info("Number of Messages Fetched:" + results.size());
        return results;
    }

	
}
