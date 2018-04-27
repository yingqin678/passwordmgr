package com.ying;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.mybatis.spring.annotation.*;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
@MapperScan("com.ying.service.mybatis")
public class ApplicationMain {
        @RequestMapping("/")
        public String greeting() {
            return "Hello World!";
        }

        public static void main(String[] args) {
            SpringApplication.run(ApplicationMain.class, args);
        }
}