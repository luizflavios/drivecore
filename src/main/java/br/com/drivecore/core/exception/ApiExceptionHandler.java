package br.com.drivecore.core.exception;

import br.com.drivecore.core.exception.model.ApiExceptionErrorDTO;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final String DETAIL_REGEX = "Detail:";

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


}
