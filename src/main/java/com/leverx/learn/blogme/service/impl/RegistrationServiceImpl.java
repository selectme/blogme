package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.service.ActivationCodeService;
import com.leverx.learn.blogme.service.RegistrationService;
import org.springframework.stereotype.Service;

/**
 * @author Viktar on 07.06.2020
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final ActivationCodeService activationCodeService;

    public RegistrationServiceImpl(ActivationCodeService activationCodeService) {
        this.activationCodeService = activationCodeService;
    }

    @Override
    public String activateUserByConfirmationCode(String confirmationCode) {
       return activationCodeService.activateUserByCode(confirmationCode);
    }



}
