package br.com.drivecore.domain.employer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployerSummaryResponseDTO {

    private UUID id;
    private String name;

}
