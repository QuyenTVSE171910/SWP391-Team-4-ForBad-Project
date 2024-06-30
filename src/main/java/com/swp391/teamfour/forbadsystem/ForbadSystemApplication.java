package com.swp391.teamfour.forbadsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ForbadSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForbadSystemApplication.class, args);
    }

}
