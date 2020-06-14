package com.leverx.learn.blogme.repository;

import com.leverx.learn.blogme.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Viktar on 27.05.2020
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
}
