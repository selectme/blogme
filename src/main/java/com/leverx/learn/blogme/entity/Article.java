package com.leverx.learn.blogme.entity;

import javafx.scene.text.Text;

import java.util.Date;

/**
 * @author Viktar on 27.05.2020
 */
public class Article {

    private Integer id;

    private String title;

    private Text text;

    private Enum status;

    private Integer author_id;

    private Date created_at;

    private Date updated_at;

}
