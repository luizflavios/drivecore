package br.com.drivecore.controller.expense;

import br.com.drivecore.application.expense.ExpenseApplicationService;
import br.com.drivecore.controller.expense.model.CreateExpenseRequestDTO;
import br.com.drivecore.controller.expense.model.ExpenseResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@Tag(name = "Expenses")
public class ExpenseController {

    private final ExpenseApplicationService expenseApplicationService;

    @PostMapping
    @Operation(summary = "Create expense")
    public ResponseEntity<ExpenseResponseDTO> createExpense(@RequestBody @Valid CreateExpenseRequestDTO createExpenseRequestDTO) {
        ExpenseResponseDTO expenseResponseDTO = expenseApplicationService.createExpense(createExpenseRequestDTO);

        return new ResponseEntity<>(expenseResponseDTO, HttpStatus.CREATED);
    }

}
