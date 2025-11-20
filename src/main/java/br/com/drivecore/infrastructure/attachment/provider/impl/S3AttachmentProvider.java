package br.com.drivecore.infrastructure.attachment.provider.impl;

import br.com.drivecore.domain.report.exception.ReportGenerationException;
import br.com.drivecore.infrastructure.attachment.provider.AttachmentProvider;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.time.Duration;
import java.util.Map;

@Slf4j
public record S3AttachmentProvider(S3Client s3Client,
                                   S3Presigner s3Presigner) implements AttachmentProvider<String> {

    private static final String PROVIDER_TYPE = "S3";
    private static final Duration PRESIGNED_URL_EXPIRATION = Duration.ofHours(1);

    @Override
    public String upload(byte[] content, String bucket, String key, String contentType) {
        log.info("Iniciando upload para S3: bucket='{}', key='{}'", bucket, key);

        try {
            // Upload do arquivo para S3
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromBytes(content));
            log.info("Arquivo enviado com sucesso para S3: s3://{}/{}", bucket, key);

            return generatePresignedUrl(bucket, key);

        } catch (Exception e) {
            log.error("Erro ao fazer upload para S3: {}", e.getMessage(), e);
            throw new ReportGenerationException("Erro ao fazer upload do relatório no S3: " + e.getMessage(), e);
        }
    }

    @Override
    public String uploadWithMetadata(byte[] content, String bucket, String key, String contentType, Map<String, String> metadata) {
        log.debug("Iniciando upload com metadados para S3: bucket='{}', key='{}', metadados='{}'", bucket, key, metadata.size());

        try {
            // Upload do arquivo com metadados
            PutObjectRequest.Builder requestBuilder = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(contentType);

            // Adicionar metadados customizados
            if (!metadata.isEmpty()) {
                requestBuilder.metadata(metadata);
            }

            PutObjectRequest putRequest = requestBuilder.build();
            s3Client.putObject(putRequest, RequestBody.fromBytes(content));
            log.info("Arquivo com metadados enviado com sucesso para S3: s3://{}/{}", bucket, key);

            return key;

        } catch (Exception e) {
            log.error("Erro ao fazer upload com metadados para S3: {}", e.getMessage(), e);
            throw new ReportGenerationException("Erro ao fazer upload do relatório no S3: " + e.getMessage(), e);
        }
    }

    @Override
    public String getPresignedUrl(String bucket, String key) {
        return generatePresignedUrl(bucket, key);
    }

    @Override
    public String getProviderType() {
        return PROVIDER_TYPE;
    }

    @Override
    public boolean validateConfiguration() {
        log.debug("Validando configuração do S3");
        try {
            if (s3Client == null || s3Presigner == null) {
                log.error("S3Client ou S3Presigner não foi injetado");
                return false;
            }
            log.info("Configuração do S3 validada com sucesso");
            return true;
        } catch (Exception e) {
            log.error("Erro ao validar configuração do S3: {}", e.getMessage(), e);
            return false;
        }
    }

    private String generatePresignedUrl(String bucket, String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(
                    b -> b.signatureDuration(PRESIGNED_URL_EXPIRATION).getObjectRequest(getObjectRequest)
            );

            String presignedUrl = presignedGetObjectRequest.url().toString();

            log.info("Presigned URL gerada com expiração de {} horas", PRESIGNED_URL_EXPIRATION.toHours());
            return presignedUrl;

        } catch (Exception e) {
            log.error("Erro ao gerar presigned URL: {}", e.getMessage(), e);
            throw new ReportGenerationException("Erro ao gerar URL assinada para download: " + e.getMessage(), e);
        }
    }
}
