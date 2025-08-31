package br.com.drivecore.domain.contract;

import br.com.drivecore.domain.contract.delivery.ContractNotFoundException;
import br.com.drivecore.infrastructure.persistence.attachment.entities.AttachmentEntity;
import br.com.drivecore.infrastructure.persistence.contract.ContractRepository;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractEntity findById(UUID id) {
        return contractRepository.findById(id).orElseThrow(() -> new ContractNotFoundException(id));
    }

    public void associateNewAttachment(ContractEntity contractEntity, AttachmentEntity attachmentEntity) {
        if (contractEntity.getAttachments() == null) {
            contractEntity.setAttachments(new HashSet<>());
        }

        contractEntity.getAttachments().add(attachmentEntity);

        contractRepository.save(contractEntity);
    }

}
