package org.springframework.samples.petclinic.websocket;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Shen Chen 
 * 
 */


// ??????????? Maybe use different room ID to differentiate the different room
@ServerEndpoint("/websocket/{roomID}/{username}")
@Component
public class WebSocketServer {

	private Session session;       
	private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    
   
    
    @OnOpen
    public void onOpen(
    	      Session session, 
    	      @PathParam("username") String username) throws IOException 
    {
        logger.info("Entered into Open");
        
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        
        String message="User:" + username + " has Joined the Chat";
        	broadcast(message);
		
    }
 
    
    
    @OnMessage
    public void onMessage(Session session, String message) throws IOException 
    {
        // Handle new messages
    	logger.info("Entered into Message: Got Message:"+message);
    	String username = sessionUsernameMap.get(session);
    	
    	if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
    	{
    		String destUsername = message.split(" ")[0].substring(1); 
    		sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
    		sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
    	}
    	else // Message to whole chat
    	{
	    	broadcast(username + ": " + message);
    	}
    }
 
    @OnClose
    public void onClose(Session session) throws IOException
    {
    	logger.info("Entered into Close");
    	
    	String username = sessionUsernameMap.get(session);
    	sessionUsernameMap.remove(session);
    	usernameSessionMap.remove(username);
        
    	String message= username + " disconnected";
        broadcast(message);
    }
 
    
    
    @OnError
    public void onError(Session session, Throwable throwable) 
    {
        // Do error handling here
    	logger.info("Entered into Error");
    }
    
    /**
     * Sending message to a specific user 
     * @param username The user you want to send message to 
     * @param message The message you want to send
     */
	private void sendMessageToPArticularUser(String username, String message) 
    {	
    	try {
    		usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }
    
	/**
	 * Sending messages to alll users 
	 * @param message The message you want to send
	 * @throws IOException 
	 */
    private static void broadcast(String message) 
    	      throws IOException 
    {	  
    	sessionUsernameMap.forEach((session, username) -> {
    		synchronized (session) {
	            try {
	                session.getBasicRemote().sendText(message);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

    
    /**
     * Show the online people 
     * @return The number of people online
     */
    public static synchronized int getOnlineCount()
    {
	     return usernameSessionMap.size();	
    }
    	  
    
    
    
    
    
    
    
}
