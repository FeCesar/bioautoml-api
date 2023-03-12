package com.bioautoml.domain.process.parameters.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.model.ProcessTypeModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "parameters_group")
public class ParametersGroupModel implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "group", nullable = false)
    private String group;

    @OneToMany(mappedBy = "parametersType")
    private List<ProcessTypeModel> processesType = new ArrayList<>();

}
