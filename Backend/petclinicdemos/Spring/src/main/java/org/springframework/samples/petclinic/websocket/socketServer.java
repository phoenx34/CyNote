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
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




@RestController
@ServerEndpoint("/webSocket/{roomName}")
@Component
public class socketServer {
 
    // 使用map来收集session，key为roomName，value为同一个房间的用户集合
    // concurrentMap的key不存在时报错，不是返回null
    private static final Map<String, Set<Session>> rooms = new ConcurrentHashMap();
    private final Logger logger = LoggerFactory.getLogger(socketServer.class);
    private static final Map<String, List<String>> messagesofChatRoom = new ConcurrentHashMap();

    
    
    
    @OnOpen
    public void connect(@PathParam("roomName") String roomName, Session session) throws Exception {
    	logger.info(" 进了@open ");
    	

//    	logger.info(" 新的file生成了 ");
//    	 File file = new File("c://temp//testFile1.txt");
//    	  if (file.createNewFile()){
//    		  logger.info("File is created!");
//            }else{
//            	logger.info("File already exists.");
//            }
//    	  FileWriter writer = new FileWriter(file);
    	  
    	  
    	  
    	logger.info(" 进了@open ");
    	
    	// 将session按照房间名来存储，将各个房间的用户隔离
        if (!rooms.containsKey(roomName)) {
            // 创建房间不存在时，创建房间
        	logger.info("创建房间不存在时，创建房间");
            Set<Session> room = new HashSet<>();
            List<String> messageHistory = new ArrayList<String>();
            logger.info("添加用户");
            // 添加用户
            room.add(session);
            rooms.put(roomName, room);
            messagesofChatRoom.put(roomName, messageHistory);
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
 
    @OnMessage
    public void receiveMsg(@PathParam("roomName") String roomName,
                           String msg, Session session) throws Exception {
        // 此处应该有html过滤
    	// !!!!!!!!!!!! we should change from session.getId() to just "User has a question that"
        msg = session.getId() + ":" + msg;
        String msg_differentVersion = "Question: "+ msg;
        System.out.println(msg);
        // 接收到信息后进行广播
        broadcast(roomName, msg);
        messagesofChatRoom.get(roomName).add(msg_differentVersion);
    }
 
    // 按照房间名进行广播
    public static void broadcast(String roomName, String msg) throws Exception {
        for (Session session : rooms.get(roomName)) {
                session.getBasicRemote().sendText(msg);
        }
    }
 
    @GetMapping("/returnmesage/{roomName}")
    public List<String> returnMessage(@PathParam("roomName") String roomName) {
    	return messagesofChatRoom.get(roomName);
    }
    
    
    
}
