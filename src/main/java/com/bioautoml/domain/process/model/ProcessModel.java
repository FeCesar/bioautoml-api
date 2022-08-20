package com.bioautoml.domain.process.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.user.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "processes")
public class ProcessModel implements BaseEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessType processType;

    @Column(nullable = false)
    private LocalDateTime startupTime = LocalDateTime.now();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus = ProcessStatus.WAITING;

    @Column
    private LocalDateTime completionTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    public ProcessDTO toDTO(){
        return ProcessDTO.builder()
                .id(this.getId())
                .processType(this.getProcessType())
                .processStatus(this.getProcessStatus())
                .startupTime(this.getStartupTime())
                .completionTime((this.getCompletionTime()))
                .user(this.getUserModel().toDTO())
                .build();
    }

}
