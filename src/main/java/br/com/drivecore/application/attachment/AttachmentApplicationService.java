package br.com.drivecore.application.attachment;

import br.com.drivecore.controller.attachment.model.CreateAttachmentRequestDTO;
import br.com.drivecore.controller.machine.wheeling.truck.model.model.ObjectReferenceDTO;
import br.com.drivecore.domain.attachment.AttachmentService;
import br.com.drivecore.domain.mapper.BaseMapper;
import br.com.drivecore.infrastructure.persistence.attachment.entities.AttachmentEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentApplicationService {

    private final AttachmentService attachmentService;

    public ObjectReferenceDTO createAttachment(CreateAttachmentRequestDTO createAttachmentRequestDTO) {
        AttachmentEntity attachmentEntity = createDomainAttachment(createAttachmentRequestDTO);

        log.info("Attachment successfully realized - Location {}", attachmentEntity.getLocation());

        return BaseMapper.INSTANCE.toIdReferenceDTO(attachmentEntity);
    }

    public AttachmentEntity createDomainAttachment(CreateAttachmentRequestDTO createAttachmentRequestDTO) {
        return attachmentService.attach(createAttachmentRequestDTO.getFile());
    }

    public InputStreamResource download(String key) {
        InputStream attachment = attachmentService.download(key);

        return new InputStreamResource(attachment);
    }

    public String generatePreSignedUrl(String key) {
        return attachmentService.generateDownloadUrl(key);
    }
}
