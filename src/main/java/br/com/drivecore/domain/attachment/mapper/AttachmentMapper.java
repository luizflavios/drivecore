package br.com.drivecore.domain.attachment.mapper;

import br.com.drivecore.controller.contract.model.CreateContractAttachmentRequestDTO;
import br.com.drivecore.controller.contract.model.CreateContractAttachmentResponseDTO;
import br.com.drivecore.infrastructure.persistence.attachment.entities.AttachmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AttachmentMapper {

    AttachmentMapper INSTANCE = Mappers.getMapper(AttachmentMapper.class);

    AttachmentEntity toEntity(CreateContractAttachmentRequestDTO createContractAttachmentRequestDTO);

    CreateContractAttachmentResponseDTO toResponse(AttachmentEntity attachmentEntity);

}
