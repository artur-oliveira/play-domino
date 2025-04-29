package org.playdomino.models.generic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenericError {
    private String code;
    private String error;
    private List<?> data;
    private String message;
}
