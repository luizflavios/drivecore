package br.com.drivecore.infrastructure.persistence.specification;

import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings({"unchecked", "rawtypes"})
public class FilterCriteriaSpecification<T> implements Specification<T> {

    private final List<FilterCriteria> criteriaList;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        var predicates = new ArrayList<Predicate>();

        for (FilterCriteria criteria : criteriaList) {
            try {
                Path<?> field = getPath(root, criteria.field());
                Class<?> fieldType = field.getJavaType();
                Object value = convertValue(criteria.value(), fieldType);

                log.debug("Processing filter: field='{}', operator='{}', value='{}'",
                        criteria.field(), criteria.operator(), value);

                switch (criteria.operator()) {

                    case EQUAL -> predicates.add(cb.equal(field, value));

                    case LIKE -> {
                        if (fieldType != String.class) {
                            log.warn("LIKE operator applied to non-String field: {}", criteria.field());
                            throw new IllegalArgumentException("LIKE can only be applied to String fields");
                        }
                        predicates.add(cb.like(cb.lower(field.as(String.class)), "%" + value.toString().toLowerCase() + "%"));
                    }

                    case IN -> {
                        if (!(value instanceof Collection<?>)) {
                            log.warn("IN operator received non-collection value for field: {}", criteria.field());
                            throw new IllegalArgumentException("IN operator requires a collection of values");
                        }
                        predicates.add(field.in((Collection<?>) value));
                    }

                    case GREATER_THAN, GREATER_THAN_OR_EQUAL_TO, LESS_THAN, LESS_THAN_OR_EQUAL_TO -> {
                        if (!Comparable.class.isAssignableFrom(fieldType)) {
                            log.warn("Comparison operator applied to non-comparable field: {}", criteria.field());
                            throw new IllegalArgumentException("Operator " + criteria.operator() + " cannot be applied to type " + fieldType);
                        }

                        var compValue = (Comparable<Object>) value;
                        Expression<? extends Comparable> typedField = field.as((Class) fieldType);

                        predicates.add(switch (criteria.operator()) {
                            case GREATER_THAN -> cb.greaterThan(typedField, compValue);
                            case GREATER_THAN_OR_EQUAL_TO -> cb.greaterThanOrEqualTo(typedField, compValue);
                            case LESS_THAN -> cb.lessThan(typedField, compValue);
                            case LESS_THAN_OR_EQUAL_TO -> cb.lessThanOrEqualTo(typedField, compValue);
                            default -> throw new IllegalStateException("Unexpected operator: " + criteria.operator());
                        });
                    }
                }

            } catch (Exception e) {
                log.error("Error processing filter {}: {}", criteria, e.getMessage(), e);
                throw e;
            }
        }

        log.debug("Total predicates applied: {}", predicates.size());
        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private Object convertValue(Object value, Class<?> targetType) {
        if (value == null) return null;
        if (targetType.isAssignableFrom(value.getClass())) return value;

        String strValue = value.toString();

        if (targetType == String.class) return strValue;
        if (targetType == Integer.class) return Integer.valueOf(strValue);
        if (targetType == Long.class) return Long.valueOf(strValue);
        if (targetType == BigDecimal.class) return new BigDecimal(strValue);
        if (targetType == LocalDate.class) return LocalDate.parse(strValue);
        if (targetType == LocalDateTime.class) return LocalDateTime.parse(strValue);
        if (targetType == UUID.class) return UUID.fromString(strValue);
        if (Enum.class.isAssignableFrom(targetType)) return Enum.valueOf((Class<Enum>) targetType, strValue);

        throw new IllegalArgumentException("Cannot convert value to type " + targetType);
    }

    private Path<?> getPath(Root<?> root, String field) {
        String[] parts = field.split("\\.");
        Path<?> path = root.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            path = path.get(parts[i]);
        }
        return path;
    }
}