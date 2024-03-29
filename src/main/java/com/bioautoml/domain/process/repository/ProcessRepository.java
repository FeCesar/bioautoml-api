package com.bioautoml.domain.process.repository;

import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.model.ProcessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcessRepository extends JpaRepository<ProcessModel, UUID> {

    Optional<ProcessModel> findById(Long id);

    Optional<List<ProcessModel>> findByProcessStatusIsOrderByStartupTime(ProcessStatus processStatus);
    List<ProcessModel> findByEmail(String email);

    long countByProcessStatusIs(ProcessStatus processStatus);

}
