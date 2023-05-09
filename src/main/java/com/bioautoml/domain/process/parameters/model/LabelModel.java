package com.bioautoml.domain.process.parameters.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.dto.LabelDTO;
import com.bioautoml.domain.process.parameters.dto.LabelSimpleDTO;
import com.bioautoml.domain.process.parameters.enums.LabelType;
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
@Table(name = "label")
public class LabelModel implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LabelType labelType;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "process_id")
    private ProcessModel processModel;


    public LabelDTO toDTO() {
        return LabelDTO.builder()
                .id(this.getId())
                .labelType(this.getLabelType())
                .value(this.getValue())
                .build();
    }

    public LabelSimpleDTO toSimpleDTO() {
        return LabelSimpleDTO.builder()
                .labelType(this.getLabelType())
                .value(this.getValue())
                .build();
    }

}
