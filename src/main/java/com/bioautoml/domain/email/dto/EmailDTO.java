package com.bioautoml.domain.email.dto;

import com.bioautoml.domain.email.enums.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDTO implements Serializable {

    private String senderEmail;
    private String subject;
    private String receiverEmail;
    private ContentType contentType;
    private String content;

}
