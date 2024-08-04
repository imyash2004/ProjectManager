package com.yash.service.impl;

import com.yash.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendEmailWithToken(String userEmail, String link) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMessage,"utf-8");

        String sub="Join Project Team Invitation";

        String text="Click on the below given link for the acceptation of project team invite....   "+link;
        helper.setSubject(sub);
        helper.setText(text,true);
        helper.setTo(userEmail);
        try {
            javaMailSender.send(mimeMessage);
        }
        catch (MailSendException e){
            throw new MailSendException("Failed to send mail");
        }


    }
}
