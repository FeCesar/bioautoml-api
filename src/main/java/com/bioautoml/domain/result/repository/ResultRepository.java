package com.bioautoml.domain.result.repository;

import com.bioautoml.domain.result.model.ResultModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResultRepository extends JpaRepository<ResultModel, UUID> {
}
