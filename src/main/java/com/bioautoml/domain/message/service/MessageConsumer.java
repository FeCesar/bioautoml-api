package com.bioautoml.domain.message.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @RabbitListener(queues = {"${application.rabbit.queues.process.dna-rna}", "${application.rabbit.queues.results.generate}"})
    public void receive(@Payload String fileBody) {
        System.out.println("Message= " + fileBody);
    }

}
