package com.example.restservice.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface MyChannel {
    String TOPIC = "demo";
    @Output(TOPIC)
    MessageChannel getChannel();
}
