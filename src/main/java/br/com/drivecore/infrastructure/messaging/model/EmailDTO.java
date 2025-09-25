package br.com.drivecore.infrastructure.messaging.model;

import java.util.Map;

public record EmailDTO(String to, String subject, String template, Map<String, Object> data) {
}
