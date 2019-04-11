package lecture_chatHistory;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ChatHistoryRepository extends CrudRepository<ChatHistory, Integer> {
	ChatHistory save(ChatHistory persisited);
	List<ChatHistory> findAll();
}
