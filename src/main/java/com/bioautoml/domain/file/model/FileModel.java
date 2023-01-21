package com.bioautoml.domain.file.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.file.dto.FileDTO;
import com.bioautoml.domain.file.enums.FileType;
import com.bioautoml.domain.process.model.ProcessModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "files")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileModel implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private ProcessModel processModel;

    public FileDTO toDTO() {
        return FileDTO.builder()
                .id(this.getId())
                .fileName(this.getFileName())
                .fileType(this.getFileType())
                .processDTO(this.processModel.toDTO())
                .build();
    }

}
