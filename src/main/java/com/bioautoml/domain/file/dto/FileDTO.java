package com.bioautoml.domain.file.dto;

import com.bioautoml.domain.file.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileDTO implements Serializable {

    private UUID id;
    private String fileName;
    private FileType fileType;
    private UUID processId;

}
