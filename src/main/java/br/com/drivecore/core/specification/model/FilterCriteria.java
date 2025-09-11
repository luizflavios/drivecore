package br.com.drivecore.core.specification.model;

import br.com.drivecore.core.specification.enums.FilterCriteriaOperator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FilterCriteria(
        @NotEmpty String field,
        @NotNull FilterCriteriaOperator operator,
        Object value
) {
}