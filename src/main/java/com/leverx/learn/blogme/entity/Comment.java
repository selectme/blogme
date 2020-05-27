package com.leverx.learn.blogme.entity;

import javafx.scene.text.Text;

import java.util.Date;

/**
 * @author Viktar on 27.05.2020
 */
public class Comment {

    private Integer id;

    private Text text;

    private Integer post_id;

    private Integer author_id;

    private Date created_at;
}
