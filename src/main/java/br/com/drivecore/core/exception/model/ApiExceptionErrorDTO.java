package br.com.drivecore.core.exception.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ApiExceptionErrorDTO {

    private String error;
    private String detail;

    public String toJson() {
        return "{" + "\"error\": \"" + getError() + "\", \"detail\": \"" + getDetail() + "\" }";
    }

}
