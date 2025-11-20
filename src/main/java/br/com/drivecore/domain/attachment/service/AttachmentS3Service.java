package br.com.drivecore.domain.attachment.service;

import br.com.drivecore.infrastructure.attachment.provider.AttachmentProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttachmentS3Service {

    private final AttachmentProvider<String> attachmentProvider;

    @Value("${aws.s3.bucket:driveco-attachments}")
    private String s3Bucket;

    public String generatePresignedUrl(String s3Key) {
        try {
            log.debug("Gerando presigned URL para S3 key: {}", s3Key);
            return attachmentProvider.getPresignedUrl(s3Bucket, s3Key);
        } catch (Exception e) {
            log.error("Erro ao gerar presigned URL para S3 key: {}", s3Key, e);
            throw new RuntimeException("Erro ao gerar presigned URL: " + e.getMessage(), e);
        }
    }

    public String uploadFileAndGetKey(byte[] fileContent, String bucket, String key, String contentType, java.util.Map<String, String> metadata) {
        try {
            log.info("Iniciando upload para S3: bucket='{}', key='{}'", bucket, key);
            attachmentProvider.uploadWithMetadata(fileContent, bucket, key, contentType, metadata);
            return key;
        } catch (Exception e) {
            log.error("Erro ao fazer upload para S3: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao fazer upload para S3: " + e.getMessage(), e);
        }
    }

}

