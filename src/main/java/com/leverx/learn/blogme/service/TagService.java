package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.Tag;

import java.util.Map;

/**
 * @author Viktar on 27.05.2020
 */
public interface TagService {

    Map<String, Long> getTagCloud();

    Tag findByName(String tagName);
}
