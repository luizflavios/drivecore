package br.com.drivecore.infrastructure.persistence.attachment.entities;

import br.com.drivecore.domain.contract.enums.AttachmentType;
import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "attachments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AttachmentEntity extends BaseEntity {

    @Column(nullable = false, length = 255)
    private String s3Key;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "attachment_type")
    private AttachmentType type;

    private String description;

    private LocalDate date;

}
