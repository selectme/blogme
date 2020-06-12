package com.leverx.learn.blogme.controller;

import com.leverx.learn.blogme.service.TagService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Viktar on 27.05.2020
 */
@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RequestMapping(value = "/tags-cloud", method = RequestMethod.GET)
    public Map<String, Long> getTagCloud(){
        return tagService.getTagCloud();
    }


}
