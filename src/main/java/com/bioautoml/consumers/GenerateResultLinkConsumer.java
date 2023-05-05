package com.bioautoml.consumers;

import com.bioautoml.domain.result.service.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateResultLinkConsumer {

    @Autowired
    private ResultService resultService;

    private static final Logger logger = LoggerFactory.getLogger(GenerateResultLinkConsumer.class);

    @RabbitListener(queues = {"${application.rabbit.queues.processes.results}"})
    public void receive(String resultMessage) {
        logger.info("received request generate result link={}", resultMessage);
        this.resultService.decode(resultMessage);
    }

}
