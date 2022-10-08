package com.bioautoml.domain.message.model;

import com.bioautoml.domain.commons.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageModel implements BaseEntity {

    private UUID id;
    private Long timestamp;
    private Object message;

}
