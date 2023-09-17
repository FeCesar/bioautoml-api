package com.bioautoml.broker;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BrokerConfig {

    @Value("${application.rabbit.queues.processes.status}")
    private String processesStatusQueue;

    @Value("${application.rabbit.queues.processes.results}")
    private String processesResultsQueue;

    @Value("${application.rabbit.queues.processes.init}")
    private String processesInitQueue;

    @Value("${application.rabbit.queues.processes.errors}")
    private String processesErrorsQueue;

    @Value("${application.rabbit.queues.processes.email}")
    private String getProcessesResultsEmailQueue;

    @Bean
    private Queue createProcessesStatusQueue() {
        return new Queue(this.processesStatusQueue, true);
    }

    @Bean
    private Queue createProcessesResultsQueue() {
        return new Queue(this.processesResultsQueue, true);
    }

    @Bean
    private Queue createProcessesInitQueue() {
        return new Queue(this.processesInitQueue, true);
    }

    @Bean
    private Queue createProcessesErrorsQueue() {
        return new Queue(this.processesErrorsQueue, true);
    }

    @Bean
    private Queue createProcessesResultsEmailQueue() {
        return new Queue(this.getProcessesResultsEmailQueue, true);
    }
}
