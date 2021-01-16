package com.mika.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application {

    @RequestMapping("/")
    String Index() {
        int x = 10;
        int y = 20;
        int ans = x + y;
        return "Hello World! " + ans;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
