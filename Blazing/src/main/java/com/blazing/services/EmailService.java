package com.blazing.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.blazing.objects.VerificationToken;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender sender;
	
	public void sendEmail(String to, VerificationToken token)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Verify your blazing account");
		String body = "Verify your blazing account at http://localhost:8080/verify/" + token.getToken();
		message.setText(body);
		sender.send(message);
	}
	
}
