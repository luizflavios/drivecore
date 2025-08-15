package br.com.drivecore.infrastructure.persistence.specification.model;

import br.com.drivecore.infrastructure.persistence.specification.enums.Operator;

public record FilterCriteria(
        String field,
        Operator operator,
        Object value
) {
}