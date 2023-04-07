package com.bioautoml.domain.process.parameters.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LabelForm {

    private List<String> trainLabels = Collections.emptyList();
    private List<String> testLabels = List.of("unknown");

}
