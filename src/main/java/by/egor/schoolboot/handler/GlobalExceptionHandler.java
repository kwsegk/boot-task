package by.egor.schoolboot.handler;

import by.egor.schoolboot.dto.ErrorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex) {
        logger.warn("Entity not found: {}", ex.getMessage());

        var dto = new ErrorResponseDto(
                "Entity was not found",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(404).body(dto);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(RuntimeException ex) {
        logger.warn("Invalid request: {}", ex.getMessage());

        var dto = new ErrorResponseDto(
                "Incorrect argument list was sent",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(400).body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationError(
            MethodArgumentNotValidException ex
    ) {
        logger.warn("Validation failed: {}", ex.getMessage());

        var dto = new ErrorResponseDto(
                "Not valid request",
                ex.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(400).body(dto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        logger.error("Unhandled exception occurred", ex);

        var dto = new ErrorResponseDto(
                "Internal server error",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(500).body(dto);
    }
}