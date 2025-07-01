package by.shumpanov.stove.stove_app_parent.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponse {
    @NotBlank(message = "token не может быть пустым")
    @JsonProperty("token")
    private String token;
}
