package br.com.drivecore.controller.employer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployerResponseDTO {

    private UUID id;
    private String socialNumber;
    private String fullName;
    private LocalDate birthDate;
    private LocalDate admissionDate;
    private LocalDateTime createdAt;

}
