package com.bioautoml.producers;

import com.bioautoml.domain.message.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailProducer {

    @Autowired
    private MessageSender messageSender;

    @Value("${application.rabbit.queues.processes.email}")
    private String queue;

    private static final Logger logger = LoggerFactory.getLogger(EmailProducer.class);

    public void sendEmail(String processId) {
        logger.info("Email from the process={} send to queue", processId);
        this.messageSender.send(processId, queue);
    }

}
