package br.com.drivecore.controller.contract.model;

import br.com.drivecore.domain.contract.enums.AttachmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateContractAttachmentRequestDTO {
    private AttachmentType type;
    private String description;
    private LocalDate date;
}

