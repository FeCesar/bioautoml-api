package com.bioautoml.domain.process.model;

import com.bioautoml.domain.commons.BaseEntity;
import com.bioautoml.domain.process.dto.ProcessByUserDTO;
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
import java.time.ZoneId;
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
    private Long startupTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProcessStatus processStatus = ProcessStatus.WAITING;

    @Column
    private Long completionTime;

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
                .user(this.getUserModel().toUserProcessDTO())
                .build();
    }

    public ProcessByUserDTO toProcessByUserDTO(){
        return ProcessByUserDTO.builder()
                .id(this.getId())
                .processType(this.getProcessType())
                .processStatus(this.getProcessStatus())
                .startupTime(this.getStartupTime())
                .completionTime((this.getCompletionTime()))
                .build();
    }

}
