package com.bioautoml.domain.process.repository;

import com.bioautoml.domain.process.model.ProcessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessModel, UUID> {

    Optional<ProcessModel> findById(UUID id);

    @Query(value = "SELECT * FROM processes WHERE user_id = :userId", nativeQuery = true)
    List<ProcessModel> findByUserId(UUID userId);

}
