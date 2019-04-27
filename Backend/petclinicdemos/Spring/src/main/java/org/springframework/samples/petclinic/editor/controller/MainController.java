package org.springframework.samples.petclinic.editor.controller;

import org.springframework.samples.petclinic.editor.handler.AudioDistributeHandler;
import org.springframework.samples.petclinic.editor.utils.Base64;
import jdk.nashorn.internal.parser.JSONParser;
import net.gjerull.etherpad.client.EPLiteClient;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SendToUser;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
public class MainController {

    private final SimpMessagingTemplate messagingTemplate;
    private static CopyOnWriteArraySet<String> users = new CopyOnWriteArraySet<>();

    private final AudioDistributeHandler audioDistributor;
    
    private java.util.Base64.Encoder encoder;
    private java.util.Base64.Decoder decoder;

    private static int AUDIO_PERIOD = 250;
    private static int DESIRED_RATE = 8000;

    EPLiteClient client;

    @Autowired
    public MainController(SimpMessagingTemplate messagingTemplate, AudioDistributeHandler audioDistributor) {
        this.messagingTemplate = messagingTemplate;
        this.audioDistributor = audioDistributor;
        this.audioDistributor.setAudioPeriod(AUDIO_PERIOD);
        this.audioDistributor.setDesiredRate(DESIRED_RATE);
        this.audioDistributor.setMessagingTemplate(messagingTemplate);
    }

    private class Property {
        private int audioPeriod;
        private int desiredRate;

        public int getAudioPeriod() {
            return audioPeriod;
        }
        public void setAudioPeriod(int audioPeriod) {
            this.audioPeriod = audioPeriod;
        }

        public int getDesiredRate() {
            return desiredRate;
        }
        public void setDesiredRate(int desiredRate) {
            this.desiredRate = desiredRate;
        }
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "index";
    }

   
    @MessageMapping(value = "/chat")
    @SendTo("/topic/getResponse")
    public String talk(@Payload String text,
                       @Header("simpSessionId") String sessionId)
            throws Exception {
        System.out.println(
                "From sessionId:【" + sessionId + "】Broadcast message:【" + text + "】");
        return "【" + sessionId + "】说:【" + text + "】";
    }

    @MessageMapping(value = "/speak")
    @SendToUser(value = "/personal")
    public String speak(@Payload String text,
                        @Header("simpSessionId") String sessionId)
            throws Exception {
        System.out.println("From sessionID: " + sessionId + " personal message:" + text);
        return text;
    }

    @MessageMapping(value = "/check_in")
    @SendToUser(value = "/property")
    public Property check_in(@Payload String text,
                             @Header("simpSessionId") String sessionId)
            throws Exception {
        users.add(sessionId);
        audioDistributor.newTask(sessionId);
        System.out.println(sessionId + " check in !");
        Property property = new Property();
        property.setAudioPeriod(AUDIO_PERIOD);
        property.setDesiredRate(DESIRED_RATE);
        return property;
    }

    @MessageMapping(value = "/event")
    public void get_event(@Payload String text,
                         @Header("simpSessionId") String sessionId)
            throws Exception {
        System.out.println(sessionId + " : " + text);
    }




    @MessageMapping(value = "/audio")
//    @SendToUser(value = "/callback")
    public String get_audio(@Payload String text,
                          @Header("simpSessionId") String sessionId)
            throws Exception {
//        System.out.println(sessionId + " callback ");
        Map<String,Object> json= JsonParserFactory.getJsonParser().parseMap(text);
        int[] result = Base64.decode((String)json.get("message"));
        audioDistributor.writeBlob(sessionId, result);
//        System.out.println(bytes[1]&0xff);

        return text;
    }

    @MessageExceptionHandler
    @SendToUser(value = "/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

//    @Scheduled(fixedRate = 1000)
    public void callback() {
        for (String user : users) {
            messagingTemplate.convertAndSendToUser(user,"/personal",
                    "To " + user + " timed message");
        }
    }
}
