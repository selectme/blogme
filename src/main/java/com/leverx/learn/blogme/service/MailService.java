package com.leverx.learn.blogme.service;

/**
 * @author Viktar on 07.06.2020
 *
 * Provides methods for working with mail.
 */
public interface MailService {

    /**
     * Allows to send e-mail.
     *
     * @param emailTo e-mail will be sent on this mailbox.
     * @param subject subject of e-mail.
     * @param message message of e-mail.
     */
    void send(String emailTo, String subject, String message);
}
