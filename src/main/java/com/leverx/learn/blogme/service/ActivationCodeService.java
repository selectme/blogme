package com.leverx.learn.blogme.service;

/**
 * @author Viktar on 06.06.2020
 *
 * Provides methods for work with activation codes
 */
public interface ActivationCodeService {

    /**
     * Allows to activate an user {@link com.leverx.learn.blogme.entity.User} using activation code.
     *
     * @param code Code which will be checked for validity.
     * @return Message with a result of user activation.
     */
    String activateUserByCode(String code);

    /**
     * Generates an activation code which is necessary for user {@link com.leverx.learn.blogme.entity.User} activation.
     *
     * @param email User's {@link com.leverx.learn.blogme.entity.User} email on which one will be sent an activation code.
     * @return Activation code
     */
    String generateActivationCode(String email);

    /**
     * Checks a status of an activation code.
     *
     * @param code Code which will be checked.
     * @return code validity.
     */
    boolean isCodeExists(String code);

    /**
     * Allows to reset of password
     *
     * @param confirmationCode Code for validity.
     * @param newPassword New password which will be set.
     */
    void resetPassword(String confirmationCode, String newPassword);
}
