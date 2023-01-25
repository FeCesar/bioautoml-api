package com.bioautoml.domain.outbox.service;

import com.bioautoml.domain.outbox.model.OutboxModel;
import com.bioautoml.domain.outbox.repository.OutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutboxService {

    @Autowired
    private OutboxRepository outboxRepository;

    private static final Logger logger = LoggerFactory.getLogger(OutboxService.class);

    public void save(OutboxModel outboxModel) {
        this.outboxRepository.save(outboxModel);
    }
}
