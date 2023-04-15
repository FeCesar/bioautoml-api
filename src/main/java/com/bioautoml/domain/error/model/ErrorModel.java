package com.bioautoml.domain.error.model;

import com.bioautoml.domain.commons.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "errors")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorModel implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String type;

    @Column
    private String message;

    @Column
    private UUID processId;

    @Column(name = "dat_created")
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(name = "reference_date")
    private LocalDateTime referenceDate;


}
