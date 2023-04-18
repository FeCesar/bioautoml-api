package com.bioautoml.domain.schedules;

import com.bioautoml.domain.message.MessageSender;
import com.bioautoml.domain.outbox.repository.OutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SendProcessService {

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private MessageSender messageSender;

    @Value("${application.rabbit.queues.processes.init}")
    private String processesInitQueue;

    private static final Logger logger = LoggerFactory.getLogger(SendProcessService.class);


    public void check() {
        this.outboxRepository.findByWasSentIs(Boolean.FALSE).forEach(outbox -> {
            this.messageSender.send(outbox.getMessage(), this.processesInitQueue);
            logger.info("Was sent idt_outbox={}, message={}", outbox.getId(), outbox.getMessage());

            outbox.setWasSent(Boolean.TRUE);
            this.outboxRepository.save(outbox);
        });
    }

}
