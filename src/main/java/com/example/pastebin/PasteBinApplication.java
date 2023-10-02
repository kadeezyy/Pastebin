package com.example.pastebin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PasteBinApplication {

    public static void main(String[] args) {
        SpringApplication.run(PasteBinApplication.class, args);
    }

}
