package com.bioautoml.domain.error.repository;

import com.bioautoml.domain.error.model.ErrorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ErrorModelRepository extends JpaRepository<ErrorModel, UUID> {
}
