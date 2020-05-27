package com.leverx.learn.blogme.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author Viktar on 27.05.2020
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.leverx.learn.blogme")
public class WebConfig  extends WebMvcConfigurationSupport {
}
