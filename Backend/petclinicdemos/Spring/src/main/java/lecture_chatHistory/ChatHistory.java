package lecture_chatHistory;

import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

// Chat history has one to one relationship with lecture
public class ChatHistory {

	@Entity
	@Table(name = "classent")
	public class classEntity {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "MessageID", nullable=false)    
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer MID;        // Message ID:  this is the index of the chat message of one lecture 
		
	    
	    
	    @Column(name = "ChatHistory")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private String ChatHistory;    // Chat History: this is the message 



	    
	    // Getters and Setters 
		public Integer getMID() {
			return MID;
		}



		public void setMID(Integer mID) {
			MID = mID;
		}



		public String getChatHistory() {
			return ChatHistory;
		}



		public void setChatHistory(String chatHistory) {
			ChatHistory = chatHistory;
		}


		
		
		
		// Tostring method 
		@Override
		public String toString() {
			return "classEntity [MID=" + MID + ", ChatHistory=" + ChatHistory + "]";
		}

	
		
	
	
	
	
	
	
	
}
}