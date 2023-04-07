package com.bioautoml.domain.process.parameters.repository;

import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.model.LabelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LabelRepository extends JpaRepository<LabelModel, UUID> {

    Optional<List<LabelModel>> findAllByProcessModel(ProcessModel processModel);

}
