package org.springframework.samples.petclinic.editor.controller;

import org.springframework.samples.petclinic.editor.dao.GroupMapper;
import org.springframework.samples.petclinic.editor.entity.EtherpadProps;
import org.springframework.samples.petclinic.editor.entity.GroupEntity;
import org.springframework.samples.petclinic.editor.entity.Login;
import org.springframework.samples.petclinic.editor.handler.AudioDistributeHandler;
import net.gjerull.etherpad.client.EPLiteClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/notepad")
public class EtherpadController {
    String sessionID = null;
    String authorID = null;

    private EtherpadProps etherpadProps;
    private EPLiteClient client;
    private GroupMapper groupMapper;
    private AudioDistributeHandler audioDistributor;

    @Autowired
    public EtherpadController(EtherpadProps etherpadProps, GroupMapper groupMapper, AudioDistributeHandler audioDistributor) {
        this.etherpadProps = etherpadProps;
        this.groupMapper = groupMapper;
        this.audioDistributor = audioDistributor;
        client = new EPLiteClient(etherpadProps.getUrl(), etherpadProps.getApiKey());
    }

    private class PadProperties {
        private String sessionID;
        private String padId;

        public String getSessionID() {
            return sessionID;
        }
        public void setSessionID(String sessionID) {
            this.sessionID = sessionID;
        }

        public String getPadId() {
            return padId;
        }
        public void setPadId(String padId) {
            this.padId = padId;
        }
    }

    @GetMapping("/getEtherpad")
    public @ResponseBody PadProperties getEtherpad(
            @RequestParam("preSessionID") String preSessionID,
            @RequestParam("groupName") String groupName,
            @RequestParam("padName") String padName,
            HttpServletRequest request,
            Model m) {
        System.out.println(groupName + "--" + padName);
        Map result =null;
        String groupID = null;
        String padID = null;
        result = client.createGroupIfNotExistsFor(groupName);
        System.out.println(result);
        groupID = (String) result.get("groupID");
        padID = groupID+"$"+padName;
        // create author
        String authorMapper = ((Login)request.getSession().getAttribute("loginInfo")).getUsername();
        System.out.println("authorMapper: "+authorMapper);
        result = client.createAuthorIfNotExistsFor(authorMapper);
        authorID = (String )result.get("authorID");
        
        if (!preSessionID.equals("null")) {
            client.deleteSession(preSessionID);
            System.out.println("deleteSession");
        }
        // create session
        result = client.createSession(groupID,authorID,1);
        System.out.println(result);
        sessionID = (String )result.get("sessionID");

        PadProperties response = new PadProperties();
        response.setSessionID(sessionID);
        response.setPadId(padID);
        return response;
    }

    @PutMapping("/group")
    public @ResponseBody String createGroup(GroupEntity group) throws Exception{
        Map result =null;
        result = client.createGroupIfNotExistsFor(group.getGroup());
        System.out.println("createGroup : "+result);
        groupMapper.save(group);
        return "hello";
    }

    @PutMapping("/pad")
    public @ResponseBody String creatPad(
            @RequestParam("groupName") String groupName,
            @RequestParam("groupPad") String groupPad) throws Exception{
        Map result =null;
        result = client.createGroupIfNotExistsFor(groupName);
        String groupID = (String) result.get("groupID");
        try {
            result = client.createGroupPad(groupID,groupPad);
            System.out.println("createGroupPad : "+result);
            String padID = (String) result.get("padID");
            client.setText(padID,"Welcome to Copoint");
            String text = client.getText(padID).get("text").toString();
            System.out.println(text);
        }catch (Exception e)
        {
            return "error";
        }
        return "ok";
    }

    @GetMapping("/access2group")
    public @ResponseBody String access2group(
            @RequestParam("groupName") String groupName,
            HttpServletRequest request,
            Model m) {
        System.out.println("access2group : " + groupName);
        String authorName = ((Login)request.getSession().getAttribute("loginInfo")).getUsername();
        List<GroupEntity> gs=groupMapper.getByGroupName(groupName);
        if (gs.size() == 0) {
            return "error";
        }
        boolean hasExisted = false;
        for (GroupEntity g : gs) {
            System.out.println(g.getMember());
            System.out.println(g.getAuthor());
            if (g.getAuthor().equals(authorName) || (g.getMember()!=null && g.getMember().equals(authorName))) {
                hasExisted = true;
            }
        }
        if (hasExisted) {
            return "hasExisted";
        }
        GroupEntity group = new GroupEntity();
        group.setAuthor(gs.get(0).getAuthor());
        group.setGroup(groupName);
        group.setMember(authorName);
        groupMapper.save(group);
        return "ok";
    }

    @GetMapping("/etherpad")
    public String etherpad(HttpServletRequest request, Model m) {
        Login loginInfo = (Login)request.getSession().getAttribute("loginInfo");
        try {
            client.createPad("readme","Welcome to Copoint !\n" +
                    "\n" +
                    "This pad text is synchronized as you type, so that everyone viewing this page sees the same text. This allows you to collaborate seamlessly on documents!\n" +
                    "\n" +
                    "Enjoy yourself !\n");
        }catch (Exception e)
        {
            System.out.println("readme pad does already exist");
        }
        String padid = (String)client.getReadOnlyID("readme").get("readOnlyID");
        m.addAttribute("userName", loginInfo.getUsername());
        m.addAttribute("readmePadId", padid);
        m.addAttribute("etherpadHost", etherpadProps.getUrl());
        return "etherpad";
    }
    @RequestMapping("/leavePage")
    public @ResponseBody String leavePage(
            @RequestParam("sessionID") String sessionID,
            @RequestParam("socket_sessionID") String socket_sessionID) {
        System.out.println("leavePage : " + socket_sessionID);
        if (!sessionID.equals("null"))
        {
            client.deleteSession(sessionID);
        }
        audioDistributor.cancelTask(socket_sessionID);
        System.out.println(audioDistributor.getSize());
        return "hello";
    }
}
