package com.example.restservice.controller;

import com.example.restservice.stream.MySender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DemoController {

    @Autowired
    private MySender sender;

    @PostMapping(value = "/demo-event", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> demo(HttpEntity<String> httpEntity) throws JsonProcessingException {
        String json = httpEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        sender.sendDemo(mapper.readValue(json, JsonNode.class));
        return ResponseEntity.ok(json);
    }

}
