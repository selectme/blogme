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

/**
 * @author Viktar on 06.06.2020
 */
@Service
public class RedisActivationLinkServiceImpl implements ActivationCodeService {

    private final RMapCache<String, String> cache;
    private final UserRepository userRepository;

    public RedisActivationLinkServiceImpl(RedissonClient redissonClient, UserRepository userRepository) {
        Assert.notNull(redissonClient, "redissonClient must not be null");
        Assert.notNull(userRepository, "userRepository must not be null");

        this.cache = redissonClient.getMapCache("activation-codes");
        this.userRepository = userRepository;
    }


    @Override
    public void activateUserByCode(String code) {

        String email = cache.get(code);
        if (!StringUtils.isEmpty(email)) {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                if (user.isEnabled()) {
                    throw new IllegalStateException("User was already activated");
                }
                user.setEnabled(true);
                userRepository.save(user);
            } else {
                throw new IllegalStateException("User was not found by email " + email);
            }
        } else {
            throw new IllegalStateException("Email was not found for code " + code);
        }

    }

    @Override
    public String generateActivationCode(String email) {
        String activationCode = UUID.randomUUID().toString();
        cache.put(activationCode, email);

        return activationCode;
    }
}
