package br.com.drivecore.application.expense;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.controller.expense.model.CreateExpenseRequestDTO;
import br.com.drivecore.controller.expense.model.ExpenseResponseDTO;
import br.com.drivecore.domain.expense.ExpenseService;
import br.com.drivecore.domain.expense.mapper.ExpenseMapper;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.expense.entities.ExpenseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExpenseApplicationService {

    private final EmployerApplicationService employerApplicationService;

    private final ExpenseService expenseService;

    public ExpenseResponseDTO createExpense(CreateExpenseRequestDTO createExpenseRequestDTO) {
        EmployerEntity createdBy = employerApplicationService.getLoggedEmployer();

        ExpenseEntity expenseEntity = ExpenseMapper.INSTANCE.toEntity(createExpenseRequestDTO, createdBy);

        expenseService.createExpense(expenseEntity);

        log.info("Expense {} successfully created by - {}", expenseEntity.getId(), createdBy.getId());

        return ExpenseMapper.INSTANCE.toExpenseResponseDTO(expenseEntity);
    }

}
