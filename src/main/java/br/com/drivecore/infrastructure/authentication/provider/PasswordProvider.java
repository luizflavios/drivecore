package br.com.drivecore.infrastructure.authentication.provider;

public interface PasswordProvider {

    String generateRandomPassword();

    String encodePassword(String password);

}
