package br.com.drivecore.domain.contract;

import br.com.drivecore.domain.contract.exception.ContractNotFoundException;
import br.com.drivecore.domain.contract.mapper.ContractMapper;
import br.com.drivecore.domain.contract.model.ContractRequestDTO;
import br.com.drivecore.domain.contract.model.CreateContractRequestDTO;
import br.com.drivecore.infrastructure.persistence.contract.ContractRepository;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import br.com.drivecore.infrastructure.persistence.employer.entities.EmployerEntity;
import br.com.drivecore.infrastructure.persistence.specification.GenericSpecification;
import br.com.drivecore.infrastructure.persistence.specification.model.FilterCriteria;
import br.com.drivecore.infrastructure.persistence.user.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractEntity createContract(CreateContractRequestDTO createContractRequestDTO,
                                         EmployerEntity responsible,
                                         UserEntity createdBy) {
        var contractEntity = ContractMapper.INSTANCE.toEntity(createContractRequestDTO, responsible, createdBy);

        return saveContractEntity(contractEntity);
    }

    public ContractEntity saveContractEntity(ContractEntity contractEntity) {
        return contractRepository.save(contractEntity);
    }

    public ContractEntity findById(UUID id) {
        return contractRepository
                .findById(id)
                .orElseThrow(() -> new ContractNotFoundException(id));
    }


    public ContractEntity updateById(UUID id, ContractRequestDTO contractRequestDTO, EmployerEntity responsible) {
        var contractEntity = findById(id);

        ContractMapper.INSTANCE.copyProperties(contractRequestDTO, contractEntity);
        contractEntity.setResponsible(responsible);

        return saveContractEntity(contractEntity);
    }

    public List<ContractEntity> findAll() {
        return contractRepository.findAll();
    }

    public List<ContractEntity> findByCriteriaFilters(List<FilterCriteria> filters) {
        var specification = new GenericSpecification<ContractEntity>(filters);

        return contractRepository.findAll(specification);
    }
}
