package br.com.drivecore.core.exception;

import br.com.drivecore.core.exception.model.ApiExceptionErrorDTO;
import br.com.drivecore.domain.contract.exception.ContractNotFoundException;
import br.com.drivecore.domain.employer.exception.EmployerNotFoundException;
import br.com.drivecore.domain.user.exception.UserNotFoundException;
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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiExceptionErrorDTO> badCredentialsExceptionHandler(BadCredentialsException badCredentialsException) {
        var apiExceptionErrorDTO = new ApiExceptionErrorDTO(badCredentialsException.getClass().getSimpleName(),
                badCredentialsException.getMessage());

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

        var apiExceptionErrorDTO = new ApiExceptionErrorDTO(methodArgumentNotValidException.getClass().getSimpleName(), detail);

        return new ResponseEntity<>(apiExceptionErrorDTO, BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiExceptionErrorDTO> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException dataIntegrityViolationException) {
        var apiExceptionErrorDTO = new ApiExceptionErrorDTO(dataIntegrityViolationException.getClass().getSimpleName(),
                dataIntegrityViolationException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, CONFLICT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiExceptionErrorDTO> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
        var apiExceptionErrorDTO = new ApiExceptionErrorDTO(httpRequestMethodNotSupportedException.getClass().getSimpleName(),
                httpRequestMethodNotSupportedException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiExceptionErrorDTO> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
        var apiExceptionErrorDTO = new ApiExceptionErrorDTO(methodArgumentTypeMismatchException.getClass().getSimpleName(),
                methodArgumentTypeMismatchException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiExceptionErrorDTO> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException httpMessageNotReadableException) {
        var apiExceptionErrorDTO = new ApiExceptionErrorDTO(httpMessageNotReadableException.getClass().getSimpleName(),
                httpMessageNotReadableException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, BAD_REQUEST);
    }


    @ExceptionHandler(EmployerNotFoundException.class)
    public ResponseEntity<ApiExceptionErrorDTO> employerNotFoundExceptionHandler(EmployerNotFoundException employerNotFoundException) {
        var apiExceptionErrorDTO = new ApiExceptionErrorDTO(employerNotFoundException.getClass().getSimpleName(),
                employerNotFoundException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, NOT_FOUND);
    }

    @ExceptionHandler(ContractNotFoundException.class)
    public ResponseEntity<ApiExceptionErrorDTO> contractNotFoundExceptionHandler(ContractNotFoundException contractNotFoundException) {
        var apiExceptionErrorDTO = new ApiExceptionErrorDTO(contractNotFoundException.getClass().getSimpleName(),
                contractNotFoundException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiExceptionErrorDTO> userNotFoundExceptionHandler(UserNotFoundException userNotFoundException) {
        var apiExceptionErrorDTO = new ApiExceptionErrorDTO(userNotFoundException.getClass().getSimpleName(),
                userNotFoundException.getMessage());

        return new ResponseEntity<>(apiExceptionErrorDTO, NOT_FOUND);
    }
}
