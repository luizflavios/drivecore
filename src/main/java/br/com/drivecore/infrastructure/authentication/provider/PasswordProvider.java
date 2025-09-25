package br.com.drivecore.infrastructure.authentication.provider;

public interface PasswordProvider {

    String generateTempPassword();

    String encodePassword(String password);

}
