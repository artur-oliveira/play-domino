package org.playdomino.models.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Country {
    BRAZIL("Brasil");
    private final String countryName;
}

