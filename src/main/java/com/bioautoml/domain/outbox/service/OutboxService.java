package com.bioautoml.domain.outbox.service;

import com.bioautoml.domain.outbox.model.OutboxModel;
import com.bioautoml.domain.outbox.repository.OutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OutboxService {

    @Autowired
    private OutboxRepository outboxRepository;

    private static final Logger logger = LoggerFactory.getLogger(OutboxService.class);

    private void save(OutboxModel outboxModel) {
        this.outboxRepository.save(outboxModel);
    }

    public void create(String message) {
        OutboxModel outboxModel = new OutboxModel();
        outboxModel.setId(UUID.randomUUID());
        outboxModel.setDateCreation(LocalDateTime.now());
        outboxModel.setMessage(message);

        this.save(outboxModel);
        logger.info("saved outbox={}", outboxModel.getId());
    }
}
