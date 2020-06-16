package com.leverx.learn.blogme.rest.controller;

import com.leverx.learn.blogme.service.TagService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST controller for {@link com.leverx.learn.blogme.entity.Tag}
 *
 * @author Viktar on 27.05.2020
 */
@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        Assert.notNull(tagService, "requestDto must not be null");

        this.tagService = tagService;
    }

    @GetMapping("/tags-cloud")
    public Map<String, Long> getTagCloud(){
        return tagService.getTagCloud();
    }

}
