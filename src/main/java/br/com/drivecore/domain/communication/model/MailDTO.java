package br.com.drivecore.domain.communication.model;

import lombok.Builder;

import java.util.List;

@Builder
public record MailDTO(String to, String subject, String text, List<MailAttachmentDTO> attachments) {
}
