package com.bioautoml.domain.message.service;

import com.bioautoml.domain.message.model.MessageModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(MessageModel message, String queueName){
        this.rabbitTemplate.convertAndSend(queueName, message);
    }
}
