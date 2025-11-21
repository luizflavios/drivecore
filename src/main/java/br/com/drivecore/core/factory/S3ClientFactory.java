package br.com.drivecore.core.factory;

import br.com.drivecore.core.configuration.AppConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@RequiredArgsConstructor
public class S3ClientFactory {

    private final AppConfiguration appConfiguration;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.SA_EAST_1)
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(appConfiguration.getAwsAccessKeyId(), appConfiguration.getAwsSecretAccessKey())
                        )
                )
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.SA_EAST_1)  // pode ser qualquer uma
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(appConfiguration.getAwsAccessKeyId(), appConfiguration.getAwsSecretAccessKey())
                        )
                )
                .build();
    }
}
