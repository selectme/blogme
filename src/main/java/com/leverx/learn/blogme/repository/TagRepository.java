package com.leverx.learn.blogme.repository;

import com.leverx.learn.blogme.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Viktar on 03.06.2020
 */
public interface TagRepository extends JpaRepository<Tag, Integer> {


    boolean existsTagByName(String name);

    Tag findByName(String tagName);
}
