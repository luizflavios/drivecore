package br.com.drivecore.domain.contract;


import br.com.drivecore.infrastructure.persistence.contract.ContractRepository;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    public void createContract(ContractEntity contract) {
        contractRepository.save(contract);
    }

}
