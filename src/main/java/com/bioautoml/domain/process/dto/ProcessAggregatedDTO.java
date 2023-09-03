package com.bioautoml.domain.process.dto;

import com.bioautoml.domain.file.dto.FileSimpleDTO;
import com.bioautoml.domain.process.parameters.dto.LabelSimpleDTO;
import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import com.bioautoml.domain.result.dto.ResultComposeAggregatedDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessAggregatedDTO implements Serializable {

    private ProcessSimpleDTO process;
    private ParametersEntity parameters;
    private List<FileSimpleDTO> files;
    private List<LabelSimpleDTO> labels;
    private ResultComposeAggregatedDTO result;

}
