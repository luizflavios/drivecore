package br.com.drivecore.application.contract;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.application.user.UserApplicationService;
import br.com.drivecore.core.generics.GenericMapper;
import br.com.drivecore.core.generics.IdReferenceGenericDTO;
import br.com.drivecore.domain.contract.ContractService;
import br.com.drivecore.domain.contract.mapper.ContractMapper;
import br.com.drivecore.domain.contract.model.ContractRequestDTO;
import br.com.drivecore.domain.contract.model.ContractResponseDTO;
import br.com.drivecore.domain.contract.model.CreateContractRequestDTO;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractApplicationService {

    private final ContractService contractService;
    private final UserApplicationService userApplicationService;
    private final EmployerApplicationService employerApplicationService;

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
}
