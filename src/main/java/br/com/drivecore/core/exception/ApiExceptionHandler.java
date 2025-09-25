package br.com.drivecore.core.exception;

import br.com.drivecore.core.exception.model.ApiExceptionErrorDTO;
import br.com.drivecore.domain.tire.exception.AlreadyExistsTirePositionException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final String USER_IS_DISABLED = "User is disabled";
    private static final String USER_CREDENTIALS_HAVE_EXPIRED = "User credentials have expired";

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiExceptionErrorDTO> badCredentialsExceptionHandler(BadCredentialsException badCredentialsException) {
        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(badCredentialsException.getClass().getSimpleName(),
                badCredentialsException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiExceptionErrorDTO> expiredJwtExceptionHandler(ExpiredJwtException expiredJwtException) {
        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(expiredJwtException.getClass().getSimpleName(),
                expiredJwtException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, UNAUTHORIZED);
    }

    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<ApiExceptionErrorDTO> credentialsExpiredExceptionHandler(CredentialsExpiredException credentialsExpiredException) {
        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(credentialsExpiredException.getClass().getSimpleName(),
                credentialsExpiredException.getMessage());

        if (USER_CREDENTIALS_HAVE_EXPIRED.equals(apiExceptionErrorDTO.getDetail())) {
            apiExceptionErrorDTO.setCredentialsExpired(TRUE);
        }

        return new ResponseEntity<>(apiExceptionErrorDTO, UNAUTHORIZED);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiExceptionErrorDTO> disabledExceptionHandler(DisabledException disabledException) {
        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(disabledException.getClass().getSimpleName(),
                disabledException.getMessage());

        if (USER_IS_DISABLED.equals(apiExceptionErrorDTO.getDetail())) {
            apiExceptionErrorDTO.setDisabledUser(TRUE);
        }

        return new ResponseEntity<>(apiExceptionErrorDTO, UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionErrorDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        var detail = methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> String.format("%s %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .distinct()
                .collect(joining("; "));

        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(methodArgumentNotValidException.getClass().getSimpleName(), detail);

        return new ResponseEntity<>(apiExceptionErrorDTO, BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiExceptionErrorDTO> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException dataIntegrityViolationException) {
        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(dataIntegrityViolationException.getClass().getSimpleName(), "Database conflict.");

        return new ResponseEntity<>(apiExceptionErrorDTO, CONFLICT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiExceptionErrorDTO> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(httpRequestMethodNotSupportedException.getClass().getSimpleName(),
                httpRequestMethodNotSupportedException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiExceptionErrorDTO> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(methodArgumentTypeMismatchException.getClass().getSimpleName(),
                methodArgumentTypeMismatchException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiExceptionErrorDTO> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException httpMessageNotReadableException) {
        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(httpMessageNotReadableException.getClass().getSimpleName(),
                httpMessageNotReadableException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiExceptionErrorDTO> entityNotFoundExceptionHandler(EntityNotFoundException entityNotFoundException) {
        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(entityNotFoundException.getClass().getSimpleName(),
                entityNotFoundException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsTirePositionException.class)
    public ResponseEntity<ApiExceptionErrorDTO> alreadyExistsTirePositionExceptionHandler(AlreadyExistsTirePositionException alreadyExistsTirePositionException) {
        ApiExceptionErrorDTO apiExceptionErrorDTO = new ApiExceptionErrorDTO(alreadyExistsTirePositionException.getClass().getSimpleName(),
                alreadyExistsTirePositionException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, UNPROCESSABLE_ENTITY);
    }

}
