package br.com.drivecore.infrastructure.authentication.provider;

import br.com.drivecore.core.configuration.AppConfiguration;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TokenProviderImpl implements TokenProvider {

    private final AppConfiguration appConfiguration;
    private final ObjectMapper objectMapper;

    @Override
    public String generateToken(User user) {
        var claims = objectMapper.convertValue(user, new TypeReference<Map<String, Object>>() {
        });

        return createToken(claims, user.getUsername());
    }

    @Override
    public void validateToken(String token) {
        Jwts.parser().verifyWith(secretKey()).build().parse(token).getPayload();
    }

    @Override
    public Claims getClaims(String token) {
        return (Claims) Jwts.parser().json(new JacksonDeserializer<>(objectMapper)).verifyWith(secretKey()).build().parse(token).getPayload();
    }

    private String createToken(Map<String, Object> claims, String subject) {
        long expirationTimeMillis = System.currentTimeMillis() + 1000 * 60 * 30;

        return Jwts.builder()
                .claims(claims)
                .json(new JacksonSerializer<>(objectMapper))
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(expirationTimeMillis))
                .signWith(secretKey()).compact();
    }

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(appConfiguration.getSecretKey()));
    }

}
