package br.com.drivecore.domain.expense;

import br.com.drivecore.infrastructure.persistence.expense.ExpenseRepository;
import br.com.drivecore.infrastructure.persistence.expense.entities.ExpenseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public void createExpense(ExpenseEntity expenseEntity) {
        expenseRepository.save(expenseEntity);
    }

}
