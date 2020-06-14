package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.User;
import com.leverx.learn.blogme.repository.UserRepository;
import com.leverx.learn.blogme.service.ActivationCodeService;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Viktar on 06.06.2020
 *
 * Implementation of {@link ActivationCodeService}
 *
 * @see ActivationCodeService
 */
@Service
public class RedisActivationLinkServiceImpl implements ActivationCodeService {

    private final RMapCache<String, String> cache;
    private final UserRepository userRepository;

    public RedisActivationLinkServiceImpl(RedissonClient redissonClient, UserRepository userRepository) {
        Assert.notNull(redissonClient, "redissconClient must not be null");
        Assert.notNull(userRepository, "userRepository must not be null");
        this.cache = redissonClient.getMapCache("activation-codes");
        this.cache.expire(24, TimeUnit.HOURS);
        this.userRepository = userRepository;
    }


    @Override
    public String activateUserByCode(String code) {
        Assert.notNull(code, "code must not be empty");

        String email = cache.get(code);
        if (!StringUtils.isEmpty(email)) {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                if (user.isEnabled()) {
                    throw new IllegalStateException("User was already activated");
                }
                user.setEnabled(true);
                userRepository.save(user);
                cache.remove(code);
                return user.getEmail() + " confirmed";
            } else {
                throw new IllegalStateException("User was not found by email " + email);
            }
        } else {
            throw new IllegalStateException("Email was not found for code " + code);
        }
    }

    @Override
    public String generateActivationCode(String email) {
        Assert.notNull(email, "email must not be empty");

        String activationCode = UUID.randomUUID().toString();
        cache.put(activationCode, email);

        return activationCode;
    }

    @Override
    public boolean isCodeExists(String code) {
        Assert.notNull(code, "code must not be empty");

        return cache.containsKey(code);
    }

    @Override
    public void resetPassword(String confirmationCode, String newPassword) {
        Assert.hasText(confirmationCode, "confirmationCode must not be empty");
        Assert.hasText(newPassword, "password must not be empty");

        if (isCodeExists(confirmationCode)) {
            String email = cache.get(confirmationCode);
            if (!StringUtils.isEmpty(email)) {
                User user = userRepository.findByEmail(email);
                if (user != null) {
                    user.setPassword(newPassword);
                    userRepository.save(user);
                    cache.remove(confirmationCode);
                } else {
                    throw new IllegalStateException("User was not found by email " + email);
                }
            } else {
                throw new IllegalStateException("Email was not found for code " + confirmationCode);
            }
        } else {
            throw new IllegalStateException("Code " + confirmationCode + "is not valid");
        }
    }

}
