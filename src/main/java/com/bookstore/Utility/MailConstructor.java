package com.bookstore.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.bookstore.domain.User;

@Component
public class MailConstructor {
	
	@Autowired
	private Environment env;
	
	public SimpleMailMessage constructNewUserEmail(User user,String password){
		String message="\n Please user below credentials to login and edit your personal information "
				+"\n Username	:	"+user.getUsername()+
				"\n	Password	:	"+password;
		
		SimpleMailMessage email=new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Le's BookStore- New User");
		email.setText(message);
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

}
