package com.leverx.learn.blogme.service.impl;

import com.leverx.learn.blogme.entity.Tag;
import com.leverx.learn.blogme.repository.TagRepository;
import com.leverx.learn.blogme.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Viktar on 03.06.2020
 */
@Service
public class TagServiceImpl implements TagService {

    private TagRepository repository;


    @Autowired
    public TagServiceImpl(TagRepository repository) {
        this.repository = repository;
    }

    @Override
    public Tag addTag(Tag tag) {
        return repository.save(tag);
    }

    @Override
    public Tag editTag(Tag tag) {
        return repository.save(tag);
    }

    @Override
    public Tag getTagById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public void deleteTag(Tag tag) {
        repository.delete(tag);
    }

    @Override
    public Set<Tag> getAllTags() {
        return (Set<Tag>) repository.findAll();
    }

    public boolean existsByName(String name) {
        return repository.existsTagByName(name);
    }

    public void saveAll(Set<Tag> tags) {
        repository.saveAll(tags);
    }

    @Override
    public Tag findByName(String tagName) {
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
