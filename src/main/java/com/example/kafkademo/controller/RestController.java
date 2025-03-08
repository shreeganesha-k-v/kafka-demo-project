package com.example.kafkademo.controller;

import com.example.kafkademo.producer.ProducerService;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private final ProducerService producerService ;

    public RestController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/publish")
    public String publish(){
        producerService.publishData();
        return "Published successfully";
    }
}
