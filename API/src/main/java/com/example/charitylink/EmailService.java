package com.example.charitylink;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    private JavaMailSender emailSender;

    public void sendPasswordResetEmail(String email, String token) {
        String subject = "Password Reset";
        String text = "The token to reset your password is:" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
