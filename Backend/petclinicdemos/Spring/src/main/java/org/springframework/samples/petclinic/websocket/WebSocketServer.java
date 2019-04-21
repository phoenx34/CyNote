package org.springframework.samples.petclinic.websocket;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.lectureEntity.Lecture;
import org.springframework.samples.petclinic.lectureEntity.LectureRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;



// roomName is constructed as below xxxxx_xx
@Component
@ServerEndpoint(value ="/webSocket/{roomName}",configurator = CustomConfigurator.class)
public class WebSocketServer {
 
    // 使用map来收集session，key为roomName，value为同一个房间的用户集合
    // concurrentMap的key不存在时报错，不是返回null
    private static final Map<String, Set<Session>> rooms = new ConcurrentHashMap();
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    // You actually need one 
    // This is for saving the chat message in the data base

    
    @Autowired
    LectureRepository lectureRepository;
    

//    @Inject
//    private LectureRepository lectureRepository;
    
    
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
 
    
    
    
    
    
    
    
 // roomName is constructed as below xxxxx_xx
    @OnMessage
    public void receiveMsg(@PathParam("roomName") String roomName,
                           String msg, Session session) throws Exception {
    	String message = "Shoutout: " + msg;
    	 System.out.println(message);
         // 接收到信息后进行广播
         broadcast(roomName, message);
    	// Here since we already have a lecture for sure for a class 
    	// So this whichLecture object will not be null 
         System.out.println("11111111111111111111111111");
         Long a = Long.parseLong(roomName);
         System.out.print(a);
         System.out.println();
     	System.out.println("2222222222222222222222222222");
     	System.out.println(lectureRepository.existsById(a));
     	System.out.println(lectureRepository.count());
//        Lecture lec = lectureRepository.findlecById(a);
    	System.out.println("3333333333333333333333");
    	Optional<Lecture> lecs = lectureRepository.findById(a);
    	System.out.println("4444444444444444444444");
    	Lecture lecccc = lecs.get();
    	System.out.println("5555554444444444444444444");
    	Hibernate.initialize(lecccc.getShoutout_history());
    	System.out.println("88888888884444444444444444444");
    	lecccc.addShoutout_TOLIst(message);
    	System.out.println("4444444444444444444444");
    	// !!!!!!!!!!!!!! The list was updated, so that the repository needs to be saved again
    	lectureRepository.save(lecccc);
    	System.out.println(lecccc.getId());
    }
 
    // 按照房间名进行广播
    public static void broadcast(String roomName, String msg) throws Exception {
        for (Session session : rooms.get(roomName)) {
                session.getBasicRemote().sendText(msg);
        }
    }
    
 
}
