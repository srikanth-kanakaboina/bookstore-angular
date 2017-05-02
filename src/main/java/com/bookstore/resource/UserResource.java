package com.bookstore.resource;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.Utility.MailConstructor;
import com.bookstore.config.SecurityUtility;
import com.bookstore.domain.User;
import com.bookstore.domain.security.Role;
import com.bookstore.domain.security.UserRole;
import com.bookstore.service.UserService;

@RestController
@RequestMapping("/user")
public class UserResource {

	@Autowired
	UserService userService;
	
	@Autowired
	private MailConstructor mailConstructor;
	
	@Autowired
	private JavaMailSender mailSender;
	
	
	@RequestMapping(value="/newUser",method=RequestMethod.POST)
	public ResponseEntity newUserPost(
			HttpServletRequest request,
			@RequestBody HashMap<String,String> mapper
			)throws Exception{
		
		String username=mapper.get("username");
		String email=mapper.get("email");
		
		if(userService.findByUsername(username)!=null){
			return new ResponseEntity("usernameExist",HttpStatus.BAD_REQUEST);
		}
		
		if(userService.findByEmail(username)!=null){
			return new ResponseEntity("emailExist",HttpStatus.BAD_REQUEST);
		}
		
		User user=new User();
		user.setUsername(username);
		user.setEmail(email);
		
		String password=SecurityUtility.randomPassword();
		
		String encrptedPassword=SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encrptedPassword);
		
		
		Role role=new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles=new HashSet<>();
		
		userRoles.add(new UserRole(user,role));
		userService.createUser(user, userRoles);
		
		SimpleMailMessage mail=mailConstructor.constructNewUserEmail(user,password);
		mailSender.send(mail);
		
		return new ResponseEntity("User Added Successfully",HttpStatus.OK);
		
		
	}
	
	
	@RequestMapping(value="/forgetPassword",method=RequestMethod.POST)
	public ResponseEntity forgetPasswordPost(
			HttpServletRequest request,
			@RequestBody String email
			)throws Exception{
		
		
		
		User user=userService.findByEmail(email);
		
		if(user==null){
			return new ResponseEntity("Email not Found",HttpStatus.BAD_REQUEST);
		}
		
		
		
		String password=SecurityUtility.randomPassword();
		
		String encrptedPassword=SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encrptedPassword);
		
		
		SimpleMailMessage newMail=mailConstructor.constructNewUserEmail(user,password);
		mailSender.send(newMail);
		
		return new ResponseEntity("Email  Sent Successfully",HttpStatus.OK);
		
		
	}
}
