package org.playdomino.models.generic;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericError {
    private String code;
    private String message;
}
