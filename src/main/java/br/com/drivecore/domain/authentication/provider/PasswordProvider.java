package br.com.drivecore.domain.authentication.provider;

public interface PasswordProvider {

    String generateTempPassword();

    String encodePassword(String password);

}
