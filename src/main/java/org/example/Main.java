package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main {
    public static Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
