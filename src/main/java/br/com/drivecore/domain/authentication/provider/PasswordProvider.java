package br.com.drivecore.domain.authentication.provider;

public interface PasswordProvider {

    String generateTempPassword(int length);

    String encodePassword(String password);

}
