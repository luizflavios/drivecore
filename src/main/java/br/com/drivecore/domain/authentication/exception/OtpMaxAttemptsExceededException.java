package br.com.drivecore.domain.authentication.exception;

public class OtpMaxAttemptsExceededException extends RuntimeException {

    public OtpMaxAttemptsExceededException(String message) {
        super(message);
    }

}
