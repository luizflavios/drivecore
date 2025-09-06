package br.com.drivecore.domain.communication.model;

import org.springframework.core.io.InputStreamSource;

public record MailAttachmentDTO(String name, InputStreamSource attachment) {
}
