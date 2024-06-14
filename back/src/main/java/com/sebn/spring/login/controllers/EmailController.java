package com.sebn.spring.login.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sebn.spring.login.models.EmailDetails;
import com.sebn.spring.login.services.EmailService;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*")
public class EmailController {

 @Autowired 
 private EmailService emailService;

 // Sending a simple Email
 @PostMapping("/sendMail")
 public String sendMail(@RequestBody EmailDetails details)
 {
      return emailService.sendSimpleMail(details);

     
 }
}
