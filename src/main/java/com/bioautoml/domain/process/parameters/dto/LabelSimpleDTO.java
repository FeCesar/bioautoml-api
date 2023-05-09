package com.bioautoml.domain.process.parameters.dto;

import com.bioautoml.domain.process.parameters.enums.LabelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabelSimpleDTO implements Serializable {

    private LabelType labelType;
    private String value;

}
