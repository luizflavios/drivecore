package br.com.drivecore.application.contract.expense;

import br.com.drivecore.application.user.UserApplicationService;
import br.com.drivecore.domain.contract.expense.ExpenseService;
import br.com.drivecore.domain.contract.expense.mapper.ExpenseMapper;
import br.com.drivecore.domain.contract.expense.model.ExpenseRequestDTO;
import br.com.drivecore.domain.contract.expense.model.ExpenseResponseDTO;
import br.com.drivecore.infrastructure.persistence.contract.expense.entities.ExpenseEntity;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseApplicationService {

    private final ExpenseService expenseService;
    private final UserApplicationService userApplicationService;

    public ExpenseEntity createExpense(@NotNull ExpenseRequestDTO expenseRequestDTO) {
        var createdBy = userApplicationService.getLoggedUser();

        return expenseService.createExpense(expenseRequestDTO, createdBy);
    }

    public ExpenseResponseDTO getExpenseById(@NotNull UUID id) {
        var expense = expenseService.findById(id);

        return ExpenseMapper.INSTANCE.toExpenseResponseDTO(expense);
    }

}
