package by.shumpanov.stove.stove_app_parent.order.exception;

import by.shumpanov.stove.stove_app_parent.constructor.exception.ForbiddenException;
import by.shumpanov.stove.stove_app_parent.security.dto.ErrorResponse;
import by.shumpanov.stove.stove_app_parent.security.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "by.shumpanov.stove.stove_app_parent.order")
@Slf4j
public class OrderExceptionHandler {

    @ExceptionHandler(ConfigurationAlreadyUsedException.class)
    public ResponseEntity<ErrorResponse> handleConfigurationAlreadyUsedException(ConfigurationAlreadyUsedException ex, WebRequest request) {
        log.error("Configuration with id: {} already used", request.getDescription(false), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Конфигурация с таким id уже занята", request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtException(Exception ex, WebRequest request) {
        log.error("Unhandled exception occurred for request: {}", request.getDescription(false), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Произошла непредвиденная ошибка", request);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        log.warn("Access denied: {}. Request: {}", ex.getMessage(), request.getDescription(false));
        return buildErrorResponse(HttpStatus.FORBIDDEN, ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(status.value())
                .timestamp(LocalDateTime.now())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();
        return new ResponseEntity<>(errorResponse, status);
    }
}