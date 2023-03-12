package com.bioautoml.domain.process.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.parameters.model.ParametersGroupModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "processes_type")
public class ProcessTypeModel implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "parameters_group_id")
    private ParametersGroupModel parametersType;

}
