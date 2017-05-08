package com.bookstore.resource;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.domain.User;
import com.bookstore.domain.UserShipping;
import com.bookstore.service.UserService;
import com.bookstore.service.UserShippingService;

@RestController
@RequestMapping("/shipping")
public class ShippingResource {
	
	@Autowired
	private UserService userservice;
	

	@Autowired
	private UserShippingService userShippingService;
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResponseEntity addNewUserShippingPost(@RequestBody UserShipping userShipping,Principal principal){
		User user=userservice.findByUsername(principal.getName());
		
		userservice.updateUserShipping(userShipping,user);
		
		return new ResponseEntity("Shipping Added(Updated) Successfully!!",HttpStatus.OK);
	} 
	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	public ResponseEntity removeShippingPost(
			@RequestBody String id,
			Principal principal
			){
//		User user = userService.findByUsername(principal.getName());
		
		userShippingService.removeById(Long.valueOf(id));
		
		return new ResponseEntity("Shipping Removed Successfully!", HttpStatus.OK);
	}
	
	@RequestMapping(value="/setDefault", method=RequestMethod.POST)
	public ResponseEntity setDefaultShippingPost(
			@RequestBody String id,
			Principal principal
			){
		User user = userservice.findByUsername(principal.getName());
		
		userservice.setUserDefaultShipping(Long.parseLong(id), user);
		
		return new ResponseEntity("Shipping Set Default Successfully!", HttpStatus.OK);
	}
	
	@RequestMapping("/getUserShippingList")
	public List<UserShipping> getUserShippingList(
			Principal principal
			){
		User user = userservice.findByUsername(principal.getName());
		
		List<UserShipping> userShippingList = user.getUserShippingList();
		
		return userShippingList;
	}
	
	
}
