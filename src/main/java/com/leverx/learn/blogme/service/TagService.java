package com.leverx.learn.blogme.service;

import com.leverx.learn.blogme.entity.Tag;

import java.util.Map;
import java.util.Set;

/**
 * @author Viktar on 27.05.2020
 */
public interface TagService {

    Tag addTag(Tag tag);

    Tag editTag(Tag tag);

    Tag getTagById(Integer id);

    void deleteTag(Tag tag);

    Set<Tag> getAllTags();

    Map<String, Long> getTagCloud();

    Tag findByName(String tagName);
}
