package com.bioautoml.domain.message.service;

import com.bioautoml.domain.message.model.MessageModel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @RabbitListener(queues = "${application.rabbit.queues.process}")
    public void receiveProcessQueue(@Payload MessageModel messageModel){
        System.out.println("PROCESS QUEUE: " + messageModel);
    }

    @RabbitListener(queues = "${application.rabbit.queues.results}")
    public void receiveResultsQueue(@Payload MessageModel messageModel){
        System.out.println("RESULTS QUEUE: " + messageModel);
    }

}
