package com.bioautoml.domain.result.dto;

import com.bioautoml.domain.commons.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDTO implements BaseEntity {

    private UUID id;
    private String link;
    private UUID processId;

}
