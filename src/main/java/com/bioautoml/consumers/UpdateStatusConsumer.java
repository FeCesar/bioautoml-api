package com.bioautoml.consumers;

import com.bioautoml.domain.process.service.ProcessUpdateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UpdateStatusConsumer {

    @Autowired
    private ProcessUpdateService processUpdateService;

    private static final Logger logger = LoggerFactory.getLogger(UpdateStatusConsumer.class);

    @RabbitListener(queues = {"${application.rabbit.queues.processes.status}"})
    public void receive(String updateMessage) {
        logger.info("received request to update process status={}", updateMessage);
        this.processUpdateService.decode(updateMessage);
    }

}
