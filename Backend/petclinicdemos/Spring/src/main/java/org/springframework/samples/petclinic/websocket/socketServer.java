package org.springframework.samples.petclinic.websocket;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lectureEntity.LectureRepository;


// roomName is the lecture number
// With a specific lecture number, you can go to that specific chat room
@ServerEndpoint("/webSocket/{roomName}")
@Component
public class socketServer {
 
    // 使用map来收集session，key为roomName，value为同一个房间的用户集合
    // concurrentMap的key不存在时报错，不是返回null
    private static final Map<String, Set<Session>> rooms = new ConcurrentHashMap();
    private final Logger logger = LoggerFactory.getLogger(socketServer.class);

    // lectureRepository is used to find the specific lecture with lecture ID 
	@Autowired
	LectureRepository lectureRepository;
	

    
    
    
    
    @OnOpen
    public void connect(@PathParam("roomName") String roomName, Session session) throws Exception {
    	
    	logger.info(" 进了@open ");
    
    	// 将session按照房间名来存储，将各个房间的用户隔离
        if (!rooms.containsKey(roomName)) {
            // 创建房间不存在时，创建房间
        	logger.info("创建房间不存在时，创建房间");
            Set<Session> room = new HashSet<>();
            logger.info("添加用户");
            // 添加用户
            room.add(session);
            rooms.put(roomName, room);
        } else {
        	logger.info("房间已存在，直接添加用户到相应的房间");
            // 房间已存在，直接添加用户到相应的房间
            rooms.get(roomName).add(session);
        }
        System.out.println("a client has connected!");
    }
 
    @OnClose
    public void disConnect(@PathParam("roomName") String roomName, Session session) {
        rooms.get(roomName).remove(session);
        System.out.println("a client has disconnected!");
    }
 
    
    
 // roomName is lecture ID
    @OnMessage
    public void receiveMsg(@PathParam("roomName") String roomName,
                           String msg, Session session) throws Exception {

    	// The roomName here is the lecture Id
    	// With the given Lecture Id, use the lecture repository to find the specific lecture
    	// get the chathistory and add the given message to the hashset 
    	
    	int result = Integer.parseInt(roomName);
    	if(lectureRepository.findById(result)==null)
    	{
    		logger.info("没有这个lecture");
    	}
    	else
    	{
    		lectureRepository.findById(result).get().getShoutout_history().add(msg);
    	}	
    	// 此处应该有html过滤
        msg = session.getId() + ":" + msg;
        System.out.println(msg);
        // 接收到信息后进行广播
        broadcast(roomName, msg);
    }
 
    
    
    // 按照房间名进行广播
    public static void broadcast(String roomName, String msg) throws Exception {
        for (Session session : rooms.get(roomName)) {
                session.getBasicRemote().sendText(msg);
        }
    }
 
}
