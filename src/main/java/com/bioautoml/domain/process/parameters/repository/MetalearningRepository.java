package com.bioautoml.domain.process.parameters.repository;

import com.bioautoml.domain.process.parameters.model.MetalearningModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MetalearningRepository extends JpaRepository<MetalearningModel, UUID> {

    @Query(value = "SELECT * FROM metalearning_parameters WHERE process_id = :id", nativeQuery = true)
    Optional<MetalearningModel> findByProcessId(Long id);

}
