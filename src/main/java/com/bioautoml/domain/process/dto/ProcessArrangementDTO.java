package com.bioautoml.domain.process.dto;

import com.bioautoml.domain.file.dto.FileDTO;
import com.bioautoml.domain.process.parameters.dto.LabelDTO;
import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProcessArrangementDTO implements Serializable {

    private ProcessByUserDTO processModel;
    private ParametersEntity parametersEntity;
    private List<LabelDTO> labels;
    private List<FileDTO> files;

}
