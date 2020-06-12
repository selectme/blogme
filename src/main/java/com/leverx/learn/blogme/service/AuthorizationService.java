package com.leverx.learn.blogme.service;

import java.util.Map;

/**
 * @author Viktar on 12.06.2020
 */
public interface AuthorizationService {

    Map<Object, Object> login(String email, String password);
}
