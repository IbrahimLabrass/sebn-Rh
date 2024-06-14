package com.sebn.spring.login.services;

import com.sebn.spring.login.models.EmailDetails;

public interface EmailService {
	
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetails details);
 

}
