package br.com.drivecore.controller.employer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SummaryEmployerResponseDTO {

    private UUID id;
    private String fullName;

}
