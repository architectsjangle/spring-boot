package com.springboot.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringBootDemoController {

    @GetMapping("/helloSpringBoot")
    public ResponseEntity<String> helloSpringBoot () {
        return new ResponseEntity<>("Hello From Spring Boot", HttpStatus.OK);
        }
}