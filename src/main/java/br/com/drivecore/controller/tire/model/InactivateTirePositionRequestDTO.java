package br.com.drivecore.controller.tire.model;

import br.com.drivecore.domain.tire.enums.TireEventType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InactivateTirePositionRequestDTO {
    @NotNull
    private TireEventType eventType;
}
