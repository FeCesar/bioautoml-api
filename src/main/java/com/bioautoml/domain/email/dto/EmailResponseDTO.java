package com.bioautoml.domain.email.dto;

import com.sendgrid.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailResponseDTO implements Serializable {

    private String body;
    private int statusCode;

    public EmailResponseDTO(Response response) {
        this.body = response.getBody();
        this.statusCode = response.getStatusCode();
    }

    @Override
    public String toString() {
        return "StatusCode: " + this.getStatusCode() + " Body: " + this.getBody();
    }

}
