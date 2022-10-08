package com.bioautoml.broker;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BrokerConfig {

    @Value("${application.rabbit.queues.process.dna-rna}")
    private String dnaRnaQueue;

    @Value("${application.rabbit.queues.process.wnm-dna-rna}")
    private String wnmDnaRnaQueue;

    @Value("${application.rabbit.queues.process.protein}")
    private String proteinQueue;

    @Value("${application.rabbit.queues.process.binary-problems}")
    private String binaryProblemsQueue;

    @Value("${application.rabbit.queues.process.multiclass-problems}")
    private String multiclassProblemsQueue;

    @Value("${application.rabbit.queues.results.generate}")
    private String generateResultsQueue;

    @Bean
    private Queue createDnaRnaQueue(){
        return new Queue(this.dnaRnaQueue, true);
    }

    @Bean
    private Queue createWnmDnaRnaQueue(){
        return new Queue(this.wnmDnaRnaQueue, true);
    }

    @Bean
    private Queue createProteinQueue(){
        return new Queue(this.proteinQueue, true);
    }

    @Bean
    private Queue createBinaryProblemsQueue(){
        return new Queue(this.binaryProblemsQueue, true);
    }

    @Bean
    private Queue createMulticlassProblemsQueue(){
        return new Queue(this.multiclassProblemsQueue, true);
    }

    @Bean
    private Queue createGenerateResultsQueue(){
        return new Queue(this.generateResultsQueue, true);
    }

}