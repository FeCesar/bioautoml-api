package com.bioautoml.domain.process.parameters.repository;

import com.bioautoml.domain.process.parameters.model.AFEMModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AFEMRepository extends JpaRepository<AFEMModel, UUID> {

    @Query(value = "SELECT * FROM afem_parameters WHERE process_id = :id", nativeQuery = true)
    Optional<AFEMModel> findByProcessId(Long id);

}
