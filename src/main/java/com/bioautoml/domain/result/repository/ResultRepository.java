package com.bioautoml.domain.result.repository;

import com.bioautoml.domain.result.model.ResultModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResultRepository extends JpaRepository<ResultModel, UUID> {

    @Query("SELECT result FROM ResultModel result WHERE result.processModel.userModel.id = :userId")
    List<ResultModel> findAllByUserId(UUID userId);

    @Query("SELECT result FROM ResultModel result WHERE result.processModel.id = :processId")
    Optional<ResultModel> findByProcess(UUID processId);

}
