package com.bioautoml.domain.result.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.result.dto.ResultDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "result_links")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResultModel implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "link")
    private String link;

    @OneToOne
    @JoinColumn(name = "process_id")
    private ProcessModel processModel;

    public ResultDTO toDTO() {
        return ResultDTO.builder()
                .id(this.getId())
                .link(this.getLink())
                .processId(this.getProcessModel().getId())
                .build();
    }
}
