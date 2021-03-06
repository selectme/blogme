package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Viktar on 07.06.2020
 *
 * Implementation of {@link MailService}
 *
 * @see MailService
 */
@Service
public class MailSenderServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    public MailSenderServiceImpl(JavaMailSender javaMailSender) {
        Assert.notNull(javaMailSender, "mailSender must not be null");

        this.javaMailSender = javaMailSender;
    }

    @Value("${mail.transport.user}")
    private String user;

    @Override
    public void send(String emailTo, String subject, String message){
        Assert.hasText(emailTo, "emailTo must not be empty");
        Assert.hasText(subject, "subject must not be empty");
        Assert.hasText(message, "message must not be empty");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(user);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }
}
