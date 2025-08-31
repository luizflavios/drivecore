package br.com.drivecore.domain.attachment.provider.impl;

import br.com.drivecore.core.configuration.AppConfiguration;
import br.com.drivecore.domain.attachment.provider.AttachmentProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class S3AttachmentProviderImpl implements AttachmentProvider {

    private final AppConfiguration appConfiguration;
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Override
    public void upload(BufferedInputStream file, long size, String key) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(appConfiguration.getApiBucketName())
                .key(key)
                .build();

        RequestBody requestBody = RequestBody.fromInputStream(file, size);

        s3Client.putObject(putObjectRequest, requestBody);
    }

    @Override
    @SneakyThrows
    public InputStream download(String key) {
        GetObjectRequest getObjectRequest = getGetObjectRequestByKey(key);

        return s3Client.getObject(getObjectRequest);
    }

    private GetObjectRequest getGetObjectRequestByKey(String key) {
        return GetObjectRequest.builder()
                .bucket(appConfiguration.getApiBucketName())
                .key(key)
                .build();
    }

    @Override
    public String generateDownloadUrl(String key) {
        GetObjectRequest getObjectRequest = getGetObjectRequestByKey(key);

        GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofMinutes(10))
                .build();

        PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);

        return presignedGetObjectRequest.url().toExternalForm();
    }

}
