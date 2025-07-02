package by.shumpanov.stove.stove_app_parent.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    @NotNull(message = "statusCode не может быть пустым")
    @JsonProperty("status_code")
    private int statusCode;

    @NotNull(message = "timestamp не может быть пустым")
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("message")
    private String message;

    @NotBlank(message = "path не может быть пустым")
    @JsonProperty("path")
    private String path;
}