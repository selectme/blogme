package com.leverx.learn.blogme.service;

/**
 * @author Viktar on 06.06.2020
 */

public interface ActivationCodeService {

    void activateUserByCode(String code);

    String generateActivationCode(String email);
}
