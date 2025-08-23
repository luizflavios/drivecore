package br.com.drivecore.application.contract;

import br.com.drivecore.application.employer.EmployerApplicationService;
import br.com.drivecore.controller.contract.model.ContractResponseDTO;
import br.com.drivecore.controller.contract.model.CreateContractRequestDTO;
import br.com.drivecore.domain.contract.ContractService;
import br.com.drivecore.domain.contract.mapper.ContractMapper;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractApplicationService {

    private final EmployerApplicationService employerApplicationService;
    private final ContractService contractService;

    public ContractResponseDTO createContract(@Valid CreateContractRequestDTO createContractRequestDTO) {
        ContractEntity contractEntity = createDomainContract(createContractRequestDTO);

        log.info("Contract {} successfully created by - {}", contractEntity.getId(), contractEntity.getCreatedBy().getId());

        return ContractMapper.INSTANCE.toContractResponseDTO(contractEntity);
    }

    public ContractEntity createDomainContract(CreateContractRequestDTO createContractRequestDTO) {
        EmployerEntity createdBy = employerApplicationService.getLoggedEmployer();

        ContractEntity contractEntity = ContractMapper.INSTANCE.toEntity(createContractRequestDTO, createdBy);

        contractService.createContract(contractEntity);

        return contractEntity;
    }

}
