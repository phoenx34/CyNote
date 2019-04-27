package org.springframework.samples.petclinic.editor.controller;

import org.springframework.samples.petclinic.editor.dao.UserMapper;
import org.springframework.samples.petclinic.editor.entity.Login;
import org.springframework.samples.petclinic.editor.entity.UserEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;

import java.util.List;

@Controller
public class LoginController {
	 @Autowired
	    UserMapper userMapper;
	    public UserEntity validateUser(Login login) {
	        UserEntity user = new UserEntity();
	        user.setName(login.getUsername());
	        user.setPassword(login.getPassword());
	        List<UserEntity> users = userMapper.findOneUserEntity(user);
//	        List<UserEntity> users = userMapper.findOne(login.getUsername(),login.getPassword());
	        return users.size() > 0 ? users.get(0) : null;
	    }
	    
	    @GetMapping(value = "/padlogin")
	    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
	        ModelAndView mav = new ModelAndView("login");
	        mav.addObject("login", new Login());
	        return mav;
	    }
	    @PostMapping(value = "/padlogin")
	    public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
	                              @ModelAttribute("login") Login login) {
	        
	        ModelAndView mav = null;
	        boolean flag = true;

	        if (validateUser(login)==null)
	        {
	            flag=false;
	        }
	        if(flag){
	            
	            System.out.println("Sucess");
	            request.getSession().setAttribute("loginInfo",login);
	            mav = new ModelAndView("redirect:/etherpad");
	            mav.addObject("username", login.getUsername());
	        }else
	        {
	            System.out.println("Failed");
	            mav = new ModelAndView("login");
	            mav.addObject("message", "Username or Password is wrong!!");
	        }
	        return mav;
	}
}
