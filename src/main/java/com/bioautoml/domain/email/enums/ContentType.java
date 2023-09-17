package com.bioautoml.domain.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentType {
    TEXT_PLAIN("text/plain"),
    HTML("html");

    private final String type;
}
