package br.com.drivecore.application.contract;

import br.com.drivecore.controller.contract.model.*;
import br.com.drivecore.core.configuration.AppConfiguration;
import br.com.drivecore.domain.attachment.mapper.AttachmentMapper;
import br.com.drivecore.domain.attachment.mapper.AttachmentService;
import br.com.drivecore.domain.attachment.service.AttachmentS3Service;
import br.com.drivecore.domain.contract.delivery.ContractService;
import br.com.drivecore.domain.expense.mapper.ExpenseMapper;
import br.com.drivecore.domain.expense.mapper.ExpenseService;
import br.com.drivecore.infrastructure.persistence.attachment.entities.AttachmentEntity;
import br.com.drivecore.infrastructure.persistence.contract.entities.ContractEntity;
import br.com.drivecore.infrastructure.persistence.expense.entities.ExpenseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ContractApplicationService {

    private final ContractService contractService;
    private final ExpenseService expenseService;
    private final AttachmentService attachmentService;
    private final AttachmentS3Service attachmentS3Service;
    private final AppConfiguration appConfiguration;

    @Transactional
    public CreateContractExpenseResponseDTO createContractExpense(UUID id,
                                                                  CreateContractExpenseRequestDTO createContractExpenseRequestDTO) {
        ContractEntity contractEntity = contractService.findContractById(id);

        ExpenseEntity expense =
                ExpenseMapper.INSTANCE.toEntity(createContractExpenseRequestDTO);

        if (contractEntity.getExpenses() == null) {
            contractEntity.setExpenses(new HashSet<>());
        }

        expenseService.saveExpense(expense);

        contractEntity.getExpenses().add(expense);
        contractService.saveContract(contractEntity);

        log.info("Expense successfully added to contract {}", id);

        return ExpenseMapper.INSTANCE.toResponse(expense);
    }

    @Transactional
    public CreateContractAttachmentResponseDTO createContractAttachment(UUID contractId,
                                                                        CreateContractAttachmentRequestDTO createContractAttachmentRequestDTO,
                                                                        byte[] fileContent,
                                                                        String fileName,
                                                                        String contentType) {
        ContractEntity contractEntity = contractService.findContractById(contractId);

        String s3Key = generateS3Key(contractId, fileName);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("attachment-type", createContractAttachmentRequestDTO.getType().getCode());
        metadata.put("contract-id", contractId.toString());
        metadata.put("original-filename", fileName);

        String returnedS3Key = attachmentS3Service.uploadFileAndGetKey(
                fileContent,
                appConfiguration.getApiBucketName(),
                s3Key,
                contentType,
                metadata
        );

        AttachmentEntity attachment = AttachmentEntity.builder()
                .type(createContractAttachmentRequestDTO.getType())
                .description(createContractAttachmentRequestDTO.getDescription())
                .date(createContractAttachmentRequestDTO.getDate())
                .s3Key(returnedS3Key)
                .fileName(fileName)
                .build();

        AttachmentEntity savedAttachment = attachmentService.saveAttachment(attachment);

        if (contractEntity.getAttachments() == null) {
            contractEntity.setAttachments(new HashSet<>());
        }

        contractEntity.getAttachments().add(savedAttachment);
        contractService.saveContract(contractEntity);

        log.info("Attachment successfully added to contract {}", contractId);

        return AttachmentMapper.INSTANCE.toResponse(savedAttachment);
    }

    public AttachmentDownloadResponseDTO getAttachmentDownloadUrl(UUID attachmentId) {
        AttachmentEntity attachment = attachmentService.findAttachmentById(attachmentId);

        String presignedUrl = attachmentS3Service.generatePresignedUrl(attachment.getS3Key());

        log.info("Presigned URL generated for attachment {}", attachmentId);

        AttachmentDownloadResponseDTO response = new AttachmentDownloadResponseDTO(presignedUrl);
        response.setId(attachment.getId());
        response.setType(attachment.getType());
        response.setDescription(attachment.getDescription());
        response.setDate(attachment.getDate());
        response.setFileName(attachment.getFileName());
        response.setCreatedAt(attachment.getCreatedAt());

        return response;
    }

    private String generateS3Key(UUID contractId, String fileName) {
        long timestamp = System.currentTimeMillis();
        return String.format("contracts/%s/attachments/%d_%s", contractId, timestamp, fileName);
    }

}
