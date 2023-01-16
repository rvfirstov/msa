package com.example.restservice.stream;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@Slf4j
public class MySender {

    @Autowired
    private MyChannel channel;

    public void sendDemo(final JsonNode inMessage) {
        log.info("Sending message to topic demo.{}", inMessage);

        boolean isSuccessSent = channel.getChannel().send(MessageBuilder
                .withPayload(inMessage)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());

        if (isSuccessSent){
            log.info("success sent to topic demo.");
        }else {
            log.debug("message not sent to topic demo.");
        }
    }

}
