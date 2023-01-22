package com.bioautoml.domain.file.repository;

import com.bioautoml.domain.file.model.FileModel;
import com.bioautoml.domain.process.model.ProcessModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<FileModel, UUID> {

    Optional<List<FileModel>> findAllByProcessModel(ProcessModel processModel);

}
