package com.leverx.learn.blogme.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Viktar on 27.05.2020
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.leverx.learn.blogme")
public class WebConfig implements WebMvcConfigurer {


//
//    @Override
//    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setObjectMapper(new ObjectMapper());
////        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
//        converters.add(converter);
//    }

}
