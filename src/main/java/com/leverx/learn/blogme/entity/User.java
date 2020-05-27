package com.leverx.learn.blogme.entity;

import java.util.Date;

/**
 * @author Viktar on 27.05.2020
 */

public class User {

    private Integer id;

    private String first_name;

    private String last_name;

    private String password;

    private String email;

    private Date created_at;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}
