package com.bioautoml.domain.outbox.repository;

import com.bioautoml.domain.outbox.model.OutboxModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxModel, UUID> {
}
