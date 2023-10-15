package com.ecom.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String htmlContent) throws MessagingException {
        jakarta.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // Set the second parameter to true to indicate HTML content

        emailSender.send(mimeMessage);
    }
}
