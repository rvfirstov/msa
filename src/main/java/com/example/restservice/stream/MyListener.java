package com.example.restservice.stream;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MyListener {
    @StreamListener(value = MyChannel.TOPIC)
    public void handle(JsonNode event){
        log.debug("in message {}", event);
    }
}
