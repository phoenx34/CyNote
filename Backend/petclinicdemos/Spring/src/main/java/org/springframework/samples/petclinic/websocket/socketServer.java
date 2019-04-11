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


// roomName is constructed as below xxxxx_xx
@ServerEndpoint("/webSocket/{roomName}")
@Component
public class socketServer {
 
    // 使用map来收集session，key为roomName，value为同一个房间的用户集合
    // concurrentMap的key不存在时报错，不是返回null
    private static final Map<String, Set<Session>> rooms = new ConcurrentHashMap();
    private final Logger logger = LoggerFactory.getLogger(socketServer.class);

    // You actually need one 
    // This is for saving the chat message in the data base

    
    
    
    
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
        String lectureNum = roomName.substring(6);
        String classNum = roomName.substring(0,5);
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
