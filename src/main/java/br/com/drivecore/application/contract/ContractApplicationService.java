package br.com.drivecore.application.contract;

import br.com.drivecore.application.attachment.AttachmentApplicationService;
import br.com.drivecore.controller.attachment.model.AttachmentResponseDTO;
import br.com.drivecore.controller.attachment.model.CreateAttachmentRequestDTO;
import br.com.drivecore.controller.model.ObjectReferenceDTO;
import br.com.drivecore.domain.contract.ContractService;
import br.com.drivecore.domain.mapper.BaseMapper;
import br.com.drivecore.infrastructure.persistence.attachment.entities.AttachmentEntity;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractApplicationService {

    private final ContractService contractService;
    private final AttachmentApplicationService attachmentApplicationService;

    public ObjectReferenceDTO attachmentToContract(UUID contractId, MultipartFile attachment) {
        ContractEntity contractEntity = contractService.findById(contractId);

        AttachmentEntity attachmentEntity = attachmentApplicationService
                .createDomainAttachment(new CreateAttachmentRequestDTO(attachment));

        contractService.associateNewAttachment(contractEntity, attachmentEntity);

        log.info("Attachment successfully associated with contract - {}", contractId);

        return BaseMapper.INSTANCE.toIdReferenceDTO(attachmentEntity);
    }

    public List<AttachmentResponseDTO> getContractAttachments(UUID contractId) {
        ContractEntity contractEntity = contractService.findById(contractId);

        return contractEntity.getAttachments()
                .stream()
                .map(attachmentEntity -> {
                    String presignedUrl = attachmentApplicationService.generatePreSignedUrl(attachmentEntity.getLocation());

                    return new AttachmentResponseDTO(attachmentEntity.getLocation(), presignedUrl);
                })
                .toList();
    }
}
