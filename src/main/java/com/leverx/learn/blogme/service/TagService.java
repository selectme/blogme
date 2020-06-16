package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.Tag;

import java.util.Map;

/**
 * @author Viktar on 27.05.2020
 *
 * Provides methods for working with {@link Tag}
 */
public interface TagService {

    /**
     * Allows to get so called "tags cloud" as Map where key is {@link Tag} name and value is quantity of {@link Tag}
     * in database.
     *
     * @return Map where key is {@link Tag} name and value is quantity of {@link Tag}
     */
    Map<String, Long> getTagCloud();

    /**
     * Allows to get {@link Tag} using its name.
     *
     * @param tagName of {@link Tag}
     * @return {@link Tag}
     */
    Tag findByName(String tagName);
}
