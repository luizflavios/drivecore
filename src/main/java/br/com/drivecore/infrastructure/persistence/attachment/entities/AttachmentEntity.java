package br.com.drivecore.infrastructure.persistence.attachment.entities;

import br.com.drivecore.infrastructure.persistence.generic.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "attachments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class AttachmentEntity extends BaseEntity {

    @Column(nullable = false)
    private String location;

}
