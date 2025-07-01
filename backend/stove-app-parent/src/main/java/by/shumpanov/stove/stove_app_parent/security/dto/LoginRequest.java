package by.shumpanov.stove.stove_app_parent.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequest {

    @NotBlank(message = "email не может быть пустым")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "password не может быть пустым")
    @Size(min = 8, message = "пароль должен содержать минимум 8 символов")
    @JsonProperty("password")
    private String password;
}
