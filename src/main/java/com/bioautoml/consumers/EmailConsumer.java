package com.bioautoml.consumers;

import com.bioautoml.domain.email.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(EmailConsumer.class);

    @RabbitListener(queues = {"${application.rabbit.queues.processes.email}"})
    public void receive(String processId) {
        logger.info("received email to send for process={}", processId);
        this.emailService.sendEmail(processId);
    }

}
