package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.service.TagService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Viktar on 27.05.2020
 */
@RestController
public class TagController {

    private static final String TAG_SERVICE_NOT_EMPTY = "tagService must not be null";

    private final TagService tagService;

    public TagController(TagService tagService) {
        Assert.notNull(tagService, TAG_SERVICE_NOT_EMPTY);

        this.tagService = tagService;
    }

    @GetMapping("/tags-cloud")
    public Map<String, Long> getTagCloud(){
        return tagService.getTagCloud();
    }

}
