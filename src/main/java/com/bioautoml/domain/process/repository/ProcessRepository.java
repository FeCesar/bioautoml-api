package com.bioautoml.domain.process.repository;

import com.bioautoml.domain.process.model.ProcessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessModel, UUID> {

    Optional<ProcessModel> findById(UUID id);

}
