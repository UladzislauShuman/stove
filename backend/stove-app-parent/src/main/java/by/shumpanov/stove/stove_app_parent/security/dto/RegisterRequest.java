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
public class RegisterRequest {
    @NotBlank(message = "email не может быть пустым")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "password не может быть пустым")
    @Size(min = 8, message = "пароль должен содержать минимум 8 символов")
    @JsonProperty("password")
    private String password;

    @NotBlank(message = "номер телефона не может быть пустым")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @NotBlank(message = "full_name не может быть пустым")
    @JsonProperty("full_name")
    private String fullName;

}
