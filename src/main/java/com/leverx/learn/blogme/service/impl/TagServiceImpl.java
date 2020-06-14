package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.Tag;
import com.leverx.learn.blogme.repository.TagRepository;
import com.leverx.learn.blogme.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Viktar on 03.06.2020
 *
 * Implementation of {@link TagService}
 *
 * @see TagService
 */
@Service
public class TagServiceImpl implements TagService {

    private final TagRepository repository;

    public TagServiceImpl(TagRepository repository) {
        Assert.notNull(repository, "tagRepository must not be null");

        this.repository = repository;
    }

    @Override
    public Tag findByName(String tagName) {
        Assert.hasText(tagName, "tagName must not be empty");

        return repository.findByName(tagName);
    }

    @Override
    public Map<String, Long> getTagCloud() {
        List<Tag> allTags = repository.findAll();
        Map<String, Long> cloud = new HashMap<>();

        for (Tag tag : allTags) {
            long tagCounter = tag.getArticles().size();
            cloud.put(tag.getName(), tagCounter);
        }
        return cloud;
    }
}
