package ru.fozeton.spectrastats.backend.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(new ErrorMessage(errors));
    }

    @ExceptionHandler(PlayerNotFound.class)
    public ResponseEntity<?> playerNotFoundException(PlayerNotFound ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UnknownStatisticType.class)
    public ResponseEntity<ErrorMessage> unknownStatisticTypeException(UnknownStatisticType ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.badRequest().body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(StatisticNotFoundInPlayer.class)
    public ResponseEntity<?> statisticNotFoundInPlayerException(StatisticNotFoundInPlayer ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.notFound().build();
    }

    public record ErrorMessage(
            Object error
    ) {
    }
}
