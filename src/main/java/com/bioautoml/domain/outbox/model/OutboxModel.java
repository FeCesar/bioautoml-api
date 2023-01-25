package com.bioautoml.domain.outbox.model;

import com.bioautoml.domain.commons.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutboxModel implements BaseEntity {

    @Id
    @Column(name = "IDT_OUTBOX")
    private UUID id;

    @Column(name = "DAT_CREATED")
    private LocalDateTime dateCreation = LocalDateTime.now();

    @Column(name = "DES_MESSAGE", columnDefinition = "text")
    private String message;

    @Column(name = "WAS_SENT")
    private Boolean wasSent = Boolean.FALSE;

}
