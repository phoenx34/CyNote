package com.mockito.tests;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserRepository;
import org.springframework.samples.petclinic.user.UserService;



public class TestServices {
	
	
	@InjectMocks
	UserService userService;

	@Mock
	UserRepository repo;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	
	
//	@Test
//	public void getAccountByIdTest() {
//		when(repo.getAccountByID(1)).thenReturn(new User(1, "jDoe", "123456", "jDoe@gmail.com"));
//
//		Account acct = acctService.getAccountByID(1);
//
//		assertEquals("jDoe", acct.getUserID());
//		assertEquals("123456", acct.getPassword());
//		assertEquals("jDoe@gmail.com", acct.getEmail());
//	}

	
	/**
	 * Test 1
	 */
	@Test
	public void getUerByIdTest() {
		when(repo.getUserById(1)).thenReturn(new User(1,"Shen_1","123","12:12:12","shenchen_1@iastate.edu","user"));

		User user = userService.findUserByID(1);

		assertEquals(Interger(1), user.getUID());
		assertEquals("Shen_1", user.getScreenname());
		assertEquals("shenchen_1@iastate.edu", user.getEmail());
	}
	
	
	
	
	
	private Object Interger(int i) {
		// TODO Auto-generated method stub
		return null;
	}



	@Test
	public void getAllAccountTest() {
		
//    	this.UID = 123456789;
//    	this.screenname = "defaultconstructor";
//    	this.password = "passssssword";
//    	this.create_time = "12:12:12";
//    	this.email = "hotmail";
//    	this.type = "user";
		
		List<User> list = new ArrayList<User>();
		User acctOne = new User(1,"Shen_1","123","12:12:12","shenchen_1@iastate.edu","user");
		User acctTwo = new User(2,"Shen_2","123","12:12:13","shenchen_2@iastate.edu","user");
		User acctThree = new User(3,"Shen_3","123","12:12:14","shenchen_3@iastate.edu","user");

		list.add(acctOne);
		list.add(acctTwo);
		list.add(acctThree);

		when(repo.findAll()).thenReturn(list);

		List<User> userList = userService.getUsers();

		assertEquals(3, userList.size());
		verify(repo, times(1)).findAll();
	}


	
	
	
	
	
	
}
