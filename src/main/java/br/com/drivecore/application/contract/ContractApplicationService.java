package br.com.drivecore.application.contract;

import br.com.drivecore.application.contract.expense.ExpenseApplicationService;
import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.application.user.UserApplicationService;
import br.com.drivecore.core.generics.domain.mapper.GenericMapper;
import br.com.drivecore.core.generics.domain.model.IdReferenceGenericDTO;
import br.com.drivecore.domain.contract.ContractService;
import br.com.drivecore.domain.contract.expense.mapper.ExpenseMapper;
import br.com.drivecore.domain.contract.expense.model.ExpenseRequestDTO;
import br.com.drivecore.domain.contract.expense.model.ExpenseResponseDTO;
import br.com.drivecore.domain.contract.mapper.ContractMapper;
import br.com.drivecore.domain.contract.model.ContractRequestDTO;
import br.com.drivecore.domain.contract.model.ContractResponseDTO;
import br.com.drivecore.domain.contract.model.CreateContractRequestDTO;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import br.com.drivecore.infrastructure.persistence.contract.expense.entities.ExpenseEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractApplicationService {

    private final ContractService contractService;
    private final UserApplicationService userApplicationService;
    private final EmployerApplicationService employerApplicationService;
    private final ExpenseApplicationService expenseApplicationService;

    public IdReferenceGenericDTO createContract(@NotNull CreateContractRequestDTO createContractRequestDTO) {
        var createdBy = userApplicationService.getLoggedUser();

        var responsible = getResponsibleToCreateContract(createContractRequestDTO.getResponsible());

        var contractEntity = contractService.createContract(createContractRequestDTO, responsible, createdBy);

        log.info("Contract {} successfully created by {}", contractEntity.getId(), createdBy.getId());

        return GenericMapper.INSTANCE.toIdReferenceGenericDTO(contractEntity);
    }

    public ContractResponseDTO getContractById(@NotNull UUID id) {
        var contractEntity = contractService.findById(id);

        return ContractMapper.INSTANCE.toContractResponseDTO(contractEntity);
    }

    private EmployerEntity getResponsibleToCreateContract(IdReferenceGenericDTO idReferenceGenericDTO) {
        return nonNull(idReferenceGenericDTO)
                ? employerApplicationService.getEmployer(idReferenceGenericDTO.getId())
                : null;
    }

    public ContractResponseDTO updateContractById(UUID id, ContractRequestDTO contractRequestDTO) {
        var responsible = getResponsibleToCreateContract(contractRequestDTO.getResponsible());

        var contractEntity = contractService.updateById(id, contractRequestDTO, responsible);

        log.info("Contract {} successfully updated", contractEntity.getId());

        return ContractMapper.INSTANCE.toContractResponseDTO(contractEntity);
    }

    public List<ContractResponseDTO> getContractsFiltered(List<FilterCriteria> filters) {
        List<ContractEntity> contracts;

        if (nonNull(filters) && !filters.isEmpty()) {
            contracts = contractService.findByCriteriaFilters(filters);
        } else {
            contracts = contractService.findAll();
        }

        return contracts
                .stream()
                .map(ContractMapper.INSTANCE::toContractResponseDTO)
                .toList();
    }

    @Transactional
    public IdReferenceGenericDTO createExpenseToContract(UUID id, ExpenseRequestDTO expenseRequestDTO) {
        var expense = expenseApplicationService.createExpense(expenseRequestDTO);

        var contractEntity = contractService.findById(id);

        putExpenseOnContracts(contractEntity, expense);

        log.info("Expense {} successfully added to contract {}", expense.getId(), contractEntity.getId());

        return GenericMapper.INSTANCE.toIdReferenceGenericDTO(expense);
    }

    public ExpenseResponseDTO getExpenseById(UUID contractId, UUID expenseId) {
        var contractEntity = contractService.findById(contractId);

        var expenseEntity = contractEntity.getExpenses()
                .parallelStream()
                .filter(expense -> expense.getId().equals(expenseId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(expenseId.toString()));

        return ExpenseMapper.INSTANCE.toExpenseResponseDTO(expenseEntity);
    }

    private void putExpenseOnContracts(ContractEntity contractEntity, ExpenseEntity expense) {
        if (isNull(contractEntity.getExpenses())) {
            contractEntity.setExpenses(new HashSet<>());
        }

        contractEntity.getExpenses().add(expense);

        contractService.saveContractEntity(contractEntity);
    }
}
