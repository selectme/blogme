package com.leverx.learn.blogme.service;

/**
 * @author Viktar on 06.06.2020
 */

public interface ActivationCodeService {

    String activateUserByCode(String code);

    String generateActivationCode(String email);

    boolean isCodeExists(String code);

    void resetPassword(String confirmationCode, String newPassword);
}
