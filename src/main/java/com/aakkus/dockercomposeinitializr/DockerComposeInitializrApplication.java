package com.aakkus.dockercomposeinitializr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DockerComposeInitializrApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerComposeInitializrApplication.class, args);
    }
}