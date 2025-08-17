package br.com.drivecore.infrastructure.persistence.specification.model;

import br.com.drivecore.infrastructure.persistence.specification.enums.Operator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FilterCriteria(
        @NotEmpty String field,
        @NotNull Operator operator,
        Object value
) {
}