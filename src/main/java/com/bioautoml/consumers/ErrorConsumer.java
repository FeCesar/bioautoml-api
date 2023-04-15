package com.bioautoml.consumers;

import com.bioautoml.domain.error.service.ErrorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ErrorConsumer {

    @Autowired
    private ErrorService errorService;

    private static final Logger logger = LoggerFactory.getLogger(ErrorConsumer.class);

    @RabbitListener(queues = {"${application.rabbit.queues.processes.errors}"})
    public void receive(String encodedMessage) {
        logger.info("received error=" + encodedMessage);
        this.errorService.decode(encodedMessage);
    }

}
