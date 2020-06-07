package com.leverx.learn.blogme.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @author Viktar on 07.06.2020
 */
@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {


    @Value("${mail.transport.protocol}")
    private String protocol;

    @Value("${mail.smtps.auth}")
    private String authorization;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.user}")
    private String user;

    @Value("${mail.password}")
    private String password;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.debug}")
    private String debug;



    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();


        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(user);
        javaMailSender.setPassword(password);
        javaMailSender.setProtocol(protocol);

        Properties mailProperties = javaMailSender.getJavaMailProperties();
        mailProperties.put("mail.transport.protocol", protocol);
        mailProperties.put("mail.debug", debug);
//        mailProperties.put("mail.smtp.ssl.enable", "true");
        mailProperties.put("mail.smtp.ssl.checkserveridentity", "false");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.socketFactory.fallback", "true");
        mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return javaMailSender;
    }




}
