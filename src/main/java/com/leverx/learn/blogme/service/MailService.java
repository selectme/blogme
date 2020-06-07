package com.leverx.learn.blogme.service;

/**
 * @author Viktar on 07.06.2020
 */
public interface MailService {

    void send(String emailTo, String subject, String message);
}
