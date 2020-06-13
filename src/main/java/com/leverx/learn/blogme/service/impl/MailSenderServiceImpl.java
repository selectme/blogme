package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Viktar on 07.06.2020
 */
@Service
public class MailSenderServiceImpl implements MailService {

    private static final String EMAIL_NOT_EMPTY = "Email must not be empty";
    private static final String SUBJECT_NOT_EMPTY = "Subject must not be empty";
    private static final String MESSAGE_NOT_EMPTY = "Message must not be empty";

    private final JavaMailSender javaMailSender;

    public MailSenderServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Value("${mail.transport.user}")
    private String user;

    @Override
    public void send(String emailTo, String subject, String message){
        Assert.notNull(emailTo, EMAIL_NOT_EMPTY);
        Assert.notNull(subject, SUBJECT_NOT_EMPTY);
        Assert.notNull(message, MESSAGE_NOT_EMPTY);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(user);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
    }
}
