package br.com.drivecore.core.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfiguration {

    @Value("${API.SECRET.KEY}")
    private String secretKey;

    @Value("${API.TEMPORARY.PASSWORD}")
    private String temporaryPassword;

    @Value("${API.BUCKET.NAME}")
    private String apiBucketName;

}
