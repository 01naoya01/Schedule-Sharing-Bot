package com.example.vol6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScheduleSharingApplication {
    // KitchenSinkWebMvcConfigurer,KitchenSinkControllerで使う
    static Path downloadedContentDir;

    public static void main(String[] args) throws IOException {
        downloadedContentDir = Files.createTempDirectory("line-bot");
        SpringApplication.run(ScheduleSharingApplication.class, args);
    }

}
