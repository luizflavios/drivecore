package br.com.drivecore.domain.tire.exception;

public class AlreadyExistsTirePositionException extends RuntimeException {

    public AlreadyExistsTirePositionException(String errorMessage) {
        super(errorMessage);
    }

}
