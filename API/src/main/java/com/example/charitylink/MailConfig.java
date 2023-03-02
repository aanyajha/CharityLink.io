package com.example.charitylink;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() throws IOException {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        // Load the properties from the XML file
        Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("API/pom.xml"));

        javaMailSender.setHost(properties.getProperty("spring.mail.host"));
        javaMailSender.setPort(Integer.parseInt(properties.getProperty("spring.mail.port")));
        javaMailSender.setUsername(properties.getProperty("spring.mail.username"));
        javaMailSender.setPassword(properties.getProperty("spring.mail.password"));

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.auth", properties.getProperty("spring.mail.properties.mail.smtp.auth"));
        mailProperties.put("mail.smtp.starttls.enable", properties.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));

        javaMailSender.setJavaMailProperties(mailProperties);

        return javaMailSender;
    }
}

