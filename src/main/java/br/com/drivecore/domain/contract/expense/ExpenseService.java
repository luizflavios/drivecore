package br.com.drivecore.domain.contract.expense;

import br.com.drivecore.domain.contract.expense.exception.ExpenseNotFoundException;
import br.com.drivecore.domain.contract.expense.mapper.ExpenseMapper;
import br.com.drivecore.domain.contract.expense.model.ExpenseRequestDTO;
import br.com.drivecore.infrastructure.persistence.contract.expense.entities.ExpenseEntity;
import br.com.drivecore.infrastructure.persistence.contract.expense.entities.ExpenseRepository;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseEntity createExpense(ExpenseRequestDTO expenseRequestDTO, UserEntity createdBy) {
        var expense = ExpenseMapper.INSTANCE.toEntity(expenseRequestDTO, createdBy);

        return expenseRepository.save(expense);
    }

    public ExpenseEntity findById(@NotNull UUID id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ExpenseNotFoundException(id));
    }
}
