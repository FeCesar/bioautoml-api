package com.bioautoml.domain.result.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.result.dto.ResultDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "results")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResultModel implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "link", columnDefinition = "text")
    private String link;

    @OneToOne
    @JoinColumn(name = "process_id")
    private ProcessModel processModel;

    @Column(name = "reference_date")
    private LocalDateTime referenceDate;

    @Column(name = "dat_creation")
    private LocalDateTime creationDate = LocalDateTime.now();

    public ResultDTO toDTO() {
        return ResultDTO.builder()
                .id(this.getId())
                .link(this.getLink())
                .processId(this.getProcessModel().getId())
                .referenceDate(this.getReferenceDate())
                .creationDate(this.getCreationDate())
                .build();
    }
}
