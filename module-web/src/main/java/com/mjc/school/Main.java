package com.mjc.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Main extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println ("Simple code of spring boot war application");
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Main.class);
    }
}
