package br.com.drivecore.domain.contract.expense.exception;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

public class ExpenseNotFoundException extends EntityNotFoundException {

    public ExpenseNotFoundException(UUID id) {
        super(String.format("Expense not found - %s", id));
    }

}
