package com.bioautoml.broker;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BrokerConfig {

    @Value("${application.rabbit.queues.process}")
    private String processQueue;

    @Value("${application.rabbit.queues.results}")
    private String resultsQueue;

    @Bean
    private Queue createProcessQueue(){
        return new Queue(this.processQueue, true);
    }

    @Bean
    private Queue createResultsQueue(){
        return new Queue(this.resultsQueue, true);
    }

}
