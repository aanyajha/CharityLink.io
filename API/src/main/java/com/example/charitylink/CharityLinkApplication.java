package com.example.charitylink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CharityLinkApplication {

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("*")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE")
//                        .allowedHeaders("*");
//            }
//        };
//    }

    public static void main(String[] args) {




        SpringApplication.run(CharityLinkApplication.class, args);
    }

}
