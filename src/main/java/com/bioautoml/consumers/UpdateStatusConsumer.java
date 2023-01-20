package com.bioautoml.consumers;

import com.bioautoml.domain.process.service.ProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UpdateStatusConsumer {

    @Autowired
    private ProcessService processService;

    private static final Logger logger = LoggerFactory.getLogger(UpdateStatusConsumer.class);

    @RabbitListener(queues = {"${application.rabbit.queues.process.status}"})
    public void receive(String processId) {
        logger.info("received request to update process=" + processId);
        this.processService.updateStatus(UUID.fromString(processId.replaceAll("\"", "")));
    }

}
