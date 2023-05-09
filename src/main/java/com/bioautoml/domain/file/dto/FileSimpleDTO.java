package com.bioautoml.domain.file.dto;

import com.bioautoml.domain.file.enums.FileType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileSimpleDTO implements Serializable {

    private FileType fileType;
    private String fileName;

}
